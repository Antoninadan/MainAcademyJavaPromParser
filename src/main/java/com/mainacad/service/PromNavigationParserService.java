package com.mainacad.service;

import com.mainacad.model.Item;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class PromNavigationParserService extends Thread {
    private static final Logger LOG = Logger.getLogger(PromProductParserService.class.getName());

    private final List<Item> items;
    private final List<Thread> threads;
    private final String url;

    public PromNavigationParserService(List<Item> items, String url, List<Thread> threads) {
        this.items = items;
        this.url = url;
        this.threads = threads;
    }

    @Override
    public void run() {
        // product link extraction
        Document document = null;

        try {
            document = Jsoup.connect(url).get(); //proxy

            Element productGallery = document.getElementsByAttributeValue("data-qaid", "product_gallery").first();
            Elements productElements = productGallery.getElementsByAttributeValue("data-qaid", "product_name");
            Set<String> itemLinks = new HashSet<>();
            productElements.forEach(it -> itemLinks.add(it.attr("href")));

            int counter = 0;
            for (String link : itemLinks) {
                if (counter > 3) {
                    break;
                }
                if (link != null) {
                    PromProductParserService promProductParserService = new PromProductParserService(items, link);
                    threads.add(promProductParserService);
                    promProductParserService.start();
                    counter++;
                }
            }
        } catch (IOException e) {
            LOG.severe(String.format("Products were not extracted by %s", url));
        }

        // pagination
        try {
            if (!url.contains("pages")) {
                Element lastPageElement = document.getElementsByAttributeValue("data-qaid", "pagination_button").last();
                if (lastPageElement != null) {
                    Integer lastPage = Integer.valueOf(lastPageElement.text());
                    for (int i = 2; i <= lastPage; i++) {
                        String nextPageUrl = url + "&page=" + i;
                        PromProductParserService promProductParserService = new PromProductParserService(items, nextPageUrl);
                        threads.add(promProductParserService);
                        promProductParserService.start();
                    }
                }
            }
        } catch (Exception e) {
            LOG.severe(String.format("Pages were not extracted by %s", url));
        }
    }
}
