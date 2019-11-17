package com.mainacad;

import com.mainacad.model.Item;
import com.mainacad.model.Items;
import com.mainacad.service.PromNavigationParserService;
import com.mainacad.service.PromProductParserService;
import com.mainacad.util.ItemMapper;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SplittableRandom;
import java.util.logging.Logger;

public class AppRunner {

    private static final String BASE_URL = "https://prom.ua";
    private static final Logger LOG = Logger.getLogger(AppRunner.class.getName());

    public static void main(String[] args) {
        if (args.length == 0) {
            LOG.warning("You didnot input any keyword!");
            return;
        }

        List<Thread> threads = new ArrayList<>();
        List<Item> items = Collections.synchronizedList(new ArrayList<>());

        LOG.info("parser started!");
        try {
            String keyword = URLEncoder.encode(args[0], "UTF-8");
            String searchUrl = BASE_URL + "/search?search_term=" + keyword;
            PromNavigationParserService promNavigationParserService =
                     new PromNavigationParserService(items, searchUrl, threads);
            threads.add(promNavigationParserService);
            promNavigationParserService.start();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        boolean threadsFinished;
        do {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadsFinished = checkThreads(threads);
        } while (threadsFinished);

        LOG.info("parser finished! " + items.size() + "were extracted");
    }

//        String url = "https://prom.ua/p999585650-noutbuk-omen-dc0007ua.html";
//
//        PromProductParserService promProductParserService = new PromProductParserService(items, url);
//        promProductParserService.start();
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        if (!items.isEmpty()) {
//            LOG.info("\n" + items.get(0).toString());
//        }

//        Item item1 = new Item("111", "name1", "url1", "imageurl1",
//                new BigDecimal("0.01"), new BigDecimal("0.011"), "availability1");
//        Item item2 = new Item("222", "name2", "url2", "imageurl2",
//                new BigDecimal("0.02"), new BigDecimal("0.03"), "availability2");
//
//        items.add(item1);
//        items.add(item2);
//
//        Items itemList = new Items(items);
//        LOG.info("\n" + ItemMapper.mapToXml(itemList.getListItems()));
//    }

    private static boolean checkThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            if (thread.isAlive() || thread.getState().equals(Thread.State.NEW)) {
                return true;
            }
        }
        return false;
    }
}