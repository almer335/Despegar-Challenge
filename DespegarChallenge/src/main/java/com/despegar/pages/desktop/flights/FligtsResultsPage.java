package com.despegar.pages.desktop.flights;

import com.despegar.pages.desktop.commons.Page;
import com.despegar.webdriver.ExtendedWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class FligtsResultsPage extends Page {

    @FindBy(css = ".cluster-content.flights-cluster")
    private List<WebElement> flightsCluster;

    @FindBy(xpath = "//li[starts-with(@class,'priceItem price')]")
    private List<WebElement> flightsPriceByAirlines;

    private static final Logger logger = LoggerFactory.getLogger(FligtsResultsPage.class);

    protected FligtsResultsPage(ExtendedWebDriver webDriver) {
        super(webDriver);
    }

    public Boolean hasResults(){
        return flightsCluster.size() > 0;
    }

    private String getHighestCostFlightFromTable(){
        return String.valueOf(flightsPriceByAirlines.stream().
                mapToInt(x->
                        Integer.parseInt(getCleanPrice(x))
                ).max().getAsInt());
    }

    public void selectHighestAirlinesFromTable(){
        flightsPriceByAirlines.stream().filter(x->getCleanPrice(x).equals(getHighestCostFlightFromTable())).
                findFirst().get().click();
        webDriver.sleep(5);
    }

    private String getCleanPrice(WebElement clusterFlights){
        return clusterFlights.findElements(By.className("flight-price-label")).get(0).getText().
                replace("$", "").
                replace(".", "").trim();
    }

    private String getHighestCostFlightFromListing(){
        return String.valueOf(flightsCluster.stream().
                mapToInt(x->
                        Integer.parseInt(getCleanPrice(x))
                ).max().getAsInt());
    }

    public FlightsConfirmationPage selectHighestCostFlight(){
        flightsCluster.stream().filter(x->getCleanPrice(x).equals(getHighestCostFlightFromListing())).
                findFirst().get().findElement(By.tagName("buy-button")).click();
        webDriver.sleep(7);
        continueModalEmergents();
        return new FlightsConfirmationPage(webDriver);
    }

    private void continueModalEmergents(){
        if (!closeWarningBaggageModal()) {
            continueAgregationServices();
        }
    }

    private Boolean closeWarningBaggageModal(){
        try{
            webDriver.findElement(By.cssSelector("//em[starts-with(@class,'btn-text') and text()='Continuar sin valijas']")).click();
            webDriver.sleep(5);
            return true;
            }catch(NoSuchElementException e){
            return false;
        }
    }

    private Boolean continueAgregationServices(){
        try{
            webDriver.findElement(By.xpath("//em[starts-with(@class,'btn-text') and text()='Continuar']")).click();
            webDriver.sleep(5);
            return true;
        }catch(NoSuchElementException e){
            return false;
        }
    }

}
