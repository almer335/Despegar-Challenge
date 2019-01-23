package com.despegar.webdriver;

import com.despegar.utils.PropertiesDefault;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.text.MessageFormat;
import java.util.*;


public class WebDriverBuilder {

    private static final List<String> browserList = Arrays.asList("chrome","ie","firefox","chrome-headless");
    private String browser;
    private Properties properties = new PropertiesDefault().getProperties();

    public WebDriverBuilder(String browser) {
        this.browser = browser;
        this.validateBrowser();
        this.setBrowserDriverPath();
    }

    private void validateBrowser() {
        if (this.browser.isEmpty()){
            throw new RuntimeException("Browser argument is blank, must be defined");
        }
        if (!browserList.contains(this.browser)){
            throw new RuntimeException("Not supported browser value: " + this.browser);
        }
    }

    public WebDriver build(){
        switch (this.browser){
            case "chrome":
                return new ChromeDriver(CapabilitiesFiller.buildChrome());
            case "ie":
                return new InternetExplorerDriver(CapabilitiesFiller.buildInternetExplorer());
            case "firefox":
                return new FirefoxDriver(CapabilitiesFiller.buildFirefox());
            case "chrome-headless":
                return new ChromeDriver(CapabilitiesFiller.buildChromeHeadless());
            default:
                return new ChromeDriver(CapabilitiesFiller.buildChrome());
        }
    }

    private void setBrowserDriverPath(){
        OS os = OS.fromString(System.getProperty("os.name"));
        String driverPath = MessageFormat.format("driver.path.{0}.{1}", this.browser, os);
        String driverPropertyKey = MessageFormat.format("driver.property.{0}.{1}", this.browser, os);
        System.setProperty(properties.getProperty(driverPropertyKey), properties.getProperty(driverPath));
    }

    enum OS{
        linux, mac, win;
        public static OS fromString(String text) {
            return Arrays.stream(OS.values()).filter(value->text.toLowerCase().contains(value.name()))
                    .findFirst().orElse(null);
        }
    }

    public static class CapabilitiesFiller {


        public static ChromeOptions buildChrome(){
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setCapability("chrome", Platform.ANY);
            return chromeOptions;
        }

        public static InternetExplorerOptions buildInternetExplorer(){
            return new InternetExplorerOptions();
        }

        public static ChromeOptions buildChromeHeadless(){
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setHeadless(true);
            return chromeOptions;
        }

        public static FirefoxOptions buildFirefox(){
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setCapability("firefox", Platform.ANY);
            firefoxOptions.setCapability("marionette", true);
            return firefoxOptions;
        }

    }

}
