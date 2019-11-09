package com.mainacad;

import com.mainacad.model.Item;
import com.mainacad.model.Items;
import com.mainacad.service.PromProductParserService;
import com.mainacad.util.ItemMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class AppRunner {
    private static final Logger LOG = Logger.getLogger(AppRunner.class.getName());

    public static void main(String[] args) {
        List<Item> items = Collections.synchronizedList(new ArrayList<>());

        String url = "https://prom.ua/p999585650-noutbuk-omen-dc0007ua.html";

        PromProductParserService promProductParserService = new PromProductParserService(items, url);
        promProductParserService.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!items.isEmpty()) {
            LOG.info("\n" + items.get(0).toString());
        }

        Item item1 = new Item("111", "name1", "url1", "imageurl1",
                new BigDecimal("0.01"), new BigDecimal("0.011"), "availability1");
        Item item2 = new Item("222", "name2", "url2", "imageurl2",
                new BigDecimal("0.02"), new BigDecimal("0.03"), "availability2");

        items.add(item1);
        items.add(item2);

        Items itemList = new Items(items);
        LOG.info("\n" + ItemMapper.mapToXml(itemList.getListItems()));
    }
}
