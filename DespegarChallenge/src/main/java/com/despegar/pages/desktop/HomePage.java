package com.despegar.pages.desktop;

import com.despegar.pages.desktop.commons.Page;
import com.despegar.pages.desktop.flights.FlightsSearchPage;
import com.despegar.pages.desktop.hotels.HotelsSearchPage;
import com.despegar.webdriver.ExtendedWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class HomePage extends Page {

    @FindBy(className = "header-product-item")
    private List<WebElement> headerProductsItems;

    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);


    public HomePage(ExtendedWebDriver extendedWebDriver) {
        super(extendedWebDriver);
    }

    public FlightsSearchPage goToFlights() {
        headerProductsItems.stream().filter(x ->
                x.findElement(By.tagName("a")).getAttribute("title").equalsIgnoreCase("vuelos")).findFirst().get().click();
        webDriver.sleep(3);
        return new FlightsSearchPage(this.webDriver);
    }

    public HotelsSearchPage goToHotels() {
        headerProductsItems.stream().filter(x ->
                x.findElement(By.tagName("a")).getAttribute("title").equalsIgnoreCase("Alojamientos")).findFirst().get().click();
        webDriver.sleep(3);
        return new HotelsSearchPage(this.webDriver);
    }






}
