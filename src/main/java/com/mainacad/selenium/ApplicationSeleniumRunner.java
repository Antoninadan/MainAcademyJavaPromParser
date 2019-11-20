package com.mainacad.selenium;

import com.mainacad.AppRunner;
import com.mainacad.model.Item;
import com.mainacad.selenium.driver.WebDriverFactory;
import com.mainacad.selenium.model.Account;
import com.mainacad.selenium.service.PromAccountService;
import com.mainacad.util.Timer;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class ApplicationSeleniumRunner {
    private static final String BASE_URL = "https://prom.ua";
    private static final Logger LOG = Logger.getLogger(AppRunner.class.getName());

    public static void main(String[] args) {

//        if (args.length == 0) {
//            LOG.warning("You didnot input any keyword!");
//            return;
//        }
//
//        LOG.info("parser started!");
        WebDriver driver = WebDriverFactory.getChromeDriver();

        Account account = new Account("Petya", "12345", "Alex", "Bond",
                "5jhdf5fdd9rtgs@ukr.net");
        driver = PromAccountService.registerAccount(account,driver);

        driver.quit();
    }

}
