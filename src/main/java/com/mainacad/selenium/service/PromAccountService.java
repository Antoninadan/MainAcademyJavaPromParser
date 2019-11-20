package com.mainacad.selenium.service;

import com.mainacad.AppRunner;
import com.mainacad.selenium.model.Account;
import com.mainacad.util.Timer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PromAccountService {
    private static final Logger LOG = Logger.getLogger(PromAccountService.class.getName());
    private static final String REG_URL = "https://prom.ua/join-customer";
//    private static final String REG_URL = "https://my.prom.ua/cabinet/user/settings";

    public static WebDriver registerAccount(Account account, WebDriver driver) {

        driver.get(REG_URL);
        Timer.waitSeconds(8);
//        List<WebElement> regForm = driver.findElements(By.tagName("form")).
//                stream().filter(element -> element.getAttribute("data-qaid") != null &&
//                element.getAttribute("data-qaid").equals("register_form")).
//                collect(Collectors.toList());

        List<WebElement> forms = driver.findElements(By.tagName("form"));
        WebElement regForm = null;
        for (WebElement element : forms) {
            if (element.getAttribute("data-qaid") != null && element.getAttribute("data-qaid").equals("register_form")) {
                regForm = element;
                break;
            }
        }
        if (regForm == null) {
            LOG.info("Register form was not found!");
            return driver;
        }

        List<WebElement> inputs = regForm.findElements(By.tagName("input"));

        Timer.waitSeconds(4);
        for (WebElement input : inputs) {
            if (input.getAttribute("data-qaid") != null && input.getAttribute("data-qaid").equals("name")) {
                input.sendKeys(account.getFirstName());
            }
            if (input.getAttribute("data-qaid") != null && input.getAttribute("data-qaid").equals("email")) {
                input.sendKeys(account.getEmail());
            }
            if (input.getAttribute("data-qaid") != null && input.getAttribute("data-qaid").equals("password")) {
                input.sendKeys(account.getPassword());
            }
        }
        Timer.waitSeconds(2);
        List<WebElement> buttons = regForm.findElements(By.tagName("button"));
        for (WebElement button : buttons) {
            if (button.getAttribute("data-qaid") != null && button.getAttribute("data-qaid").equals("submit")) {
                button.submit();
                break;
            }
        }

        Timer.waitSeconds(3);
        String currentUrl = driver.getCurrentUrl();
        driver.get(currentUrl);
        Timer.waitSeconds(2);

        // continue
        WebElement profileForm = driver.findElement(By.className("b-form"));
        if (profileForm == null) {
            LOG.info("Personal data form was not found!");
            return driver;
        }

        List<WebElement> blocks = profileForm.findElements(By.className("b-form-unit"));
        for (WebElement block : blocks) {
            if (block.getAttribute("data-qaid").equals("first_name_block")) {
                List<WebElement> inputsProfile = block.findElements(By.tagName("input"));
                for (WebElement input : inputsProfile) {
                    if (input.getAttribute("data-qaid") != null && input.getAttribute("data-qaid").equals("input_field")) {
                        input.sendKeys(account.getFirstName());
                    }
                }
            }}

//
//        List<WebElement> inputsProfile = profileForm.findElements(By.tagName("input"));
//        WebElement firstNameBlock = profileForm.getAttribute("first_name_block");

            Timer.waitSeconds(4);
//        for (WebElement input : inputsProfile) {
//            if (input.getAttribute("data-qaid") != null && input.getAttribute("data-qaid").equals("first_name_block")) {
//                input.sendKeys(account.getFirstName());
//            }
//            if (input.getAttribute("data-qaid") != null && input.getAttribute("data-qaid").equals("last_name_block")) {
//                input.sendKeys(account.getSecondName());
//            }
//        }
            Timer.waitSeconds(2);
            List<WebElement> buttonsProfile = profileForm.findElements(By.tagName("button"));
            for (WebElement button : buttonsProfile) {
                if (button.getAttribute("data-qaid") != null && button.getAttribute("data-qaid").equals("save_profile")) {
                    button.submit();
                    break;
                }
            }

            Timer.waitSeconds(3);
            String currentUrlProfile = driver.getCurrentUrl();
            driver.get(currentUrlProfile);
            Timer.waitSeconds(2);

            return driver;

        }


    }