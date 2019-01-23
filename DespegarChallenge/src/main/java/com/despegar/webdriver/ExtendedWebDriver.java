package com.despegar.webdriver;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class ExtendedWebDriver implements WebDriver, JavascriptExecutor {

    private WebDriver webDriver;
    private static ThreadLocal<ExtendedWebDriver> instance = null;
    private static final int TIMEOUT_FOR_URL_LOADING = 25;

    public ExtendedWebDriver(WebDriver webDriver) {
        super();
        this.webDriver = webDriver;
        webDriver.manage().timeouts().implicitlyWait(TIMEOUT_FOR_URL_LOADING, TimeUnit.SECONDS);
        webDriver.manage().timeouts().setScriptTimeout(TIMEOUT_FOR_URL_LOADING, TimeUnit.SECONDS);
        instance = WebDriverSingleton.getInstance();
        instance.set(this);
    }

    public void get(String url) {
        webDriver.get(url);
    }

    public String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }

    public String getTitle() {
        return webDriver.getTitle();
    }

    public List<WebElement> findElements(By by) {
        return webDriver.findElements(by).stream().collect(toList());
    }

    public List<Integer> findElementsAsIntegers(By by) {
        return webDriver.findElements(by).stream().map(element->Integer.parseInt(element.getText())).collect(toList());
    }

    public List<String> findElementsAsStrings(By by) {
        return webDriver.findElements(by).stream().map(WebElement::getText).collect(toList());
    }

    public WebElement findElement(By by) {
        return webDriver.findElement(by);
    }

    public String getPageSource() {
        return webDriver.getPageSource();
    }

    public void close() {
    webDriver.close();
    }

    public void quit() {
webDriver.quit();
    }

    public Set<String> getWindowHandles() {
    return webDriver.getWindowHandles();
    }

    public String getWindowHandle() {
        return webDriver.getWindowHandle();
    }

    public TargetLocator switchTo() {
        return webDriver.switchTo();
    }

    public Navigation navigate() { return webDriver.navigate(); }

    public Options manage() {
        return webDriver.manage();
    }

    public Object executeScript(String s, Object... objects) {
        return ((JavascriptExecutor) webDriver).executeScript(s, objects);
    }

    public Object executeAsyncScript(String s, Object... objects) {
        return ((JavascriptExecutor) webDriver).executeAsyncScript(s, objects);
    }

    public void waitUntil(int timeOutInSeconds, Function<WebDriver, Boolean> isTrue) {
        new WebDriverWait(this, timeOutInSeconds).until(isTrue);
    }

    public void waitUntilWebElementIsReady(WebElement webElement){
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        wait.until(w-> ExpectedConditions.elementToBeClickable(webElement));
    }

    public void waitUntilElementIsVisible(int timeOutInSeconds, final WebElement... webElements){
        waitUntil(timeOutInSeconds, wd -> Arrays.stream(webElements).anyMatch(this::isElementDisplayed));
    }

    public boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void maximize(){
     webDriver.manage().window().maximize();
    }

    public void redimensionate(){
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dim = new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
        webDriver.manage().window().setSize(dim);
    }

    public void addCookie(String name, String value) {
        webDriver.manage().addCookie(new Cookie(name, value));
        doRefresh();
    }

    public void addCookieWithPathAndExpiryDate(String name, String value, String domain, String path, Date expiry) {
        webDriver.manage().addCookie(new Cookie(name, value, domain, path, expiry));
        doRefresh();
    }

    public Set<Cookie> getAllCookies() {
        return webDriver.manage().getCookies();
    }

    public Cookie getCookieNamed(String name) {
        return webDriver.manage().getCookieNamed(name);
    }

    public String getValueOfCookieNamed(String name) {
        return webDriver.manage().getCookieNamed(name).getValue();
    }

    public void deleteCookieNamed(String name) {
        webDriver.manage().deleteCookieNamed(name);
        doRefresh();
    }

    public void deleteAllCookies() {
        webDriver.manage().deleteAllCookies();
    }

    public void addCookiesToBrowser(Set<Cookie> cookies) {
        cookies.stream().filter(Objects::nonNull)
                .forEach(aCookie->webDriver.manage().addCookie(aCookie));
        doRefresh();
    }

    public void doRefresh(){
        webDriver.navigate().refresh();
    }

    public void sleep(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void closeDriver(){
        instance.get().deleteAllCookies();
        instance.get().quit();
    }

    public void scrollToElement(WebElement webElement){
        this.executeScript("arguments[0].scrollIntoView(true);", webElement);
    }

}
