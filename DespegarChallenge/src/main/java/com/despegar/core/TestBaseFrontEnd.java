package com.despegar.core;


import com.despegar.pages.desktop.ECommerceSiteMap;
import com.despegar.webdriver.ExtendedWebDriver;
import com.despegar.webdriver.WebDriverBuilder;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

public class TestBaseFrontEnd {

    protected ExtendedWebDriver driver;
    protected ECommerceSiteMap eCommerceSiteMap;
    private static final Logger logger = LoggerFactory.getLogger(TestBaseFrontEnd.class);

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser) {
        driver = new ExtendedWebDriver(new WebDriverBuilder(browser).build());
        try{ driver.maximize();}catch (WebDriverException e){driver.manage().window().setSize(new Dimension(1600,900));}
        eCommerceSiteMap = new ECommerceSiteMap(driver);
        logger.info("Setup done.");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.closeDriver();
        logger.info("Driver closed");
    }

}


