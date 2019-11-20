package com.mainacad.selenium.service;

import com.mainacad.selenium.model.Account;
import com.mainacad.util.Timer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.logging.Logger;

public class PromAccountService {
    private static final Logger LOG = Logger.getLogger(PromAccountService.class.getName());
    private static final String REG_URL = "https://prom.ua/join-customer";
    private static final String MAIN_URL = "https://prom.ua";
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

        // continue fill profile
        List<WebElement> personalFormElements = driver.findElements(By.tagName("div"));
        boolean secondNameConfirmed = false;
        for (WebElement element : personalFormElements) {
            if (element.getAttribute("data-qaid") != null &&
                    element.getAttribute("data-qaid").equals("Last_name_block")) {
                List<WebElement> webElement = element.findElements(By.tagName("input"));
                if (!webElement.isEmpty()) {
                    webElement.get(0).sendKeys(account.getSecondName());
                    secondNameConfirmed = true;
                    break;
                }
            }
        }

        List<WebElement> nickNameElements = driver.findElements(By.tagName("input"));
        for (WebElement element : nickNameElements) {
            if (element.getAttribute("data-qaid") != null &&
                    element.getAttribute("data-qaid").equals("nickname_input")) {
                element.sendKeys(account.getLogin());
                break;
            }
        }

        if (secondNameConfirmed) {
            List<WebElement> buttonElements = driver.findElements(By.tagName("button"));
            for (WebElement element : buttonElements) {
                if (element.getAttribute("data-qaid") != null &&
                        element.getAttribute("data-qaid").equals("save_profile")) {
                    Timer.waitSeconds(3);
                    element.click();
                    break;
                }
            }
            Timer.waitSeconds(2);
            currentUrl = driver.getCurrentUrl();
            driver.get(currentUrl);
            Timer.waitSeconds(2);
        }
        return driver;
    }

    public static WebDriver checkRegisteredUser(Account account, WebDriver driver) {
        driver.get(MAIN_URL);
        Timer.waitSeconds(3);
        List<WebElement> regElements = driver.findElements(By.tagName("span"));
        for (WebElement element : regElements) {
            if (element.getAttribute("data-qaid") != null &&
                    element.getAttribute("data-qaid").equals("reg_element")) {

                String text = element.getText();
                if (text.contains(account.getFirstName()) && text.contains(account.getSecondName()))
                    Timer.waitSeconds(3);
                return driver;
            }
        }
        driver.quit();
        return null;
    }
}