package com.despegar.pages.desktop.hotels;

import com.despegar.pages.desktop.commons.Page;
import com.despegar.webdriver.ExtendedWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HotelsResultsPage extends Page {

    @FindBy(xpath = "//span[@data-ga-ea='filter-Stars' and @data-ga-el='5']")
    private WebElement fiveStarsFilter;

    @FindBy(css = ".hf-cluster-pricebox.cluster-pricebox-container.-eva-3-bc-yellow-3")
    private List<WebElement> hotelsClusterList;

    private static final Logger logger = LoggerFactory.getLogger(HotelsResultsPage.class);

    protected HotelsResultsPage(ExtendedWebDriver webDriver) {
        super(webDriver);
    }

    public void setFiveStarsFilter(){
        fiveStarsFilter.click();
        webDriver.sleep(8);
    }

    private String getCleanPrice(WebElement clusterHotel){

        return clusterHotel.findElements(By.xpath("ul/div/div[2]/li")).get(0).getText().
                replace("$", "").
                replace(".", "").trim();
    }

    private String getLowestCostHotel(){
        return String.valueOf(hotelsClusterList.stream().
                mapToInt(x->
                        Integer.parseInt(getCleanPrice(x))
                ).min().getAsInt());
    }

    public HotelsProductViewPage selectLowestCostHotel(){
        hotelsClusterList.stream().filter(x->getCleanPrice(x).equals(getLowestCostHotel())).
                findFirst().get().findElement(By.xpath("*//em[@class='btn-text' and text()='Ver detalle']")).click();
        webDriver.sleep(5);
        switchToNewTab();
        webDriver.sleep(2);
        return new HotelsProductViewPage(webDriver);
    }

    private void switchToNewTab(){
        ArrayList<String> tabs = new ArrayList<> (webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(1));
    }


}
