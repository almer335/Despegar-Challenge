package com.despegar.pages.desktop.hotels;

import com.despegar.pages.desktop.commons.Page;
import com.despegar.webdriver.ExtendedWebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HotelsProductViewPage extends Page {

    @FindBy(xpath = "//em[starts-with(@class,'btn-text') and @data-ga-ea='seeRooms']")
    private WebElement seeRooms;

    private static final Logger logger = LoggerFactory.getLogger(HotelsProductViewPage.class);

    protected HotelsProductViewPage(ExtendedWebDriver webDriver) {
        super(webDriver);
    }

    public Boolean isSeeRoomsDisplayed(){
        return seeRooms.isDisplayed();
    }
}

