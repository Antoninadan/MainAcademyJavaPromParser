package com.mainacad.selenium.driver;

import com.mainacad.util.Timer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class WebDriverFactory {
    private static final String MAIN_DIR = System.getProperty("user.dir");
    private static final String SEPARATOR = System.getProperty("file.separator");
    private static final String DRIVER_PATH = MAIN_DIR + SEPARATOR + "files" + SEPARATOR + "chromedriver.exe";

 public static WebDriver getChromeDriver(){
//     ChromeOptions options = new ChromeOptions();
//     options.setProxy(bmcnbkjgxblgxfj);

     System.setProperty("webdriver.chrome.driver", DRIVER_PATH);

     WebDriver driver = new ChromeDriver();
     driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
     driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

     Timer.waitSeconds(2);
     return driver;
 };
}
