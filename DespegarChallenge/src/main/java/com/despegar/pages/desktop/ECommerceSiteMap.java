package com.despegar.pages.desktop;


import com.despegar.webdriver.ExtendedWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;


public class ECommerceSiteMap {

    private final ExtendedWebDriver extendedWebDriver;

    private final String homePageUrl="https://www.despegar.com";

    public ECommerceSiteMap(ExtendedWebDriver extendedWebDriver){
        this.extendedWebDriver = extendedWebDriver;
    }

    public HomePage homePage() {
        extendedWebDriver.get(this.homePageUrl);
        closeInitialModal();
        return new HomePage(this.extendedWebDriver);
    }

    private void closeInitialModal(){
        try{
            extendedWebDriver.findElement(By.className("as-login-close")).click();
        }catch(NoSuchElementException e){
        }
    }
}