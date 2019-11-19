package com.mainacad.selenium.driver;

import com.mainacad.util.Timer;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class WebDriverFactory {
    private static final String MAIN_DIR = System.getProperty("user.dir");
    private static final String SEPARATOR = System.getProperty("file.separator");
    private static final String DRIVER_PATH = MAIN_DIR + SEPARATOR + "files" + SEPARATOR + "chromedriver.exe";
    private static final String PROXY = "81.162.224.62:50612";
    private static final String PROXY_2 = "192.162.132.51:1080";
    private static final String PROXY_3 = "81.162.224.38:50612";

 public static WebDriver getChromeDriver(){

     ChromeOptions option = new ChromeOptions();

     Proxy proxy = new Proxy();
     proxy.setHttpProxy(PROXY_3);
     option.setProxy(proxy);

     System.setProperty("webdriver.chrome.driver", DRIVER_PATH);

     WebDriver driver = new ChromeDriver(option);
     driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
     driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

     Timer.waitSeconds(2);
     return driver;
 };
}
