package com.despegar.pages.desktop.flights;

import com.despegar.pages.desktop.commons.Page;
import com.despegar.utils.CalendarSupport;
import com.despegar.webdriver.ExtendedWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class FlightsSearchPage extends Page {

    @FindBy(id = "searchbox-sbox-all-boxes")
    private WebElement searchBox;

    @FindBy( xpath = "//input[contains(@class,'flight-roundtrip-origin-input')]")
    private WebElement originInput;

    @FindBy( xpath = "//input[contains(@class,'flight-roundtrip-destination-input')]")
    private WebElement destinationInput;

    @FindBy( className = "datepicker-flights-main")
    private WebElement datePicker;

   @FindBy(xpath = "//*[@class='_dpmg2--controls-next']//i")
    private WebElement nextMonth;

    @FindBy( xpath = "//input[contains(@class,'flight-start-date-input')]")
    private WebElement startDateInput;

    @FindBy( xpath = "//input[contains(@class,'flight-end-date-input')]")
    private WebElement endDateInput;

    @FindBy( xpath = "//div[starts-with(@class,'sbox-places-dates-distri-button-container')]/div/div[2]/div[3]/div/a/em")
    private WebElement btnBuscar;

    private static final Logger logger = LoggerFactory.getLogger(FlightsSearchPage.class);

    CalendarSupport calendarSupport = new CalendarSupport();

    public FlightsSearchPage(ExtendedWebDriver webDriver) {
        super(webDriver);
    }

    public void selectOrigin(String origin){
        this.originInput.click();
        this.originInput.clear();
        this.originInput.sendKeys(origin);
        webDriver.sleep(2);
        List<WebElement> cityAutoSuggest = webDriver.findElements(By.className("ac-container")).get(1).findElements(By.className("item"));
        cityAutoSuggest.stream().filter(x->x.getText().toLowerCase().contains(origin.toLowerCase())).findFirst().get().click();
    }

    public void selectDestination(String destination){
        this.destinationInput.click();
        this.destinationInput.clear();
        this.destinationInput.sendKeys(destination);
        webDriver.sleep(2);
        List<WebElement> cityAutoSuggest = webDriver.findElements(By.className("ac-container")).get(1).findElements(By.className("item"));
        cityAutoSuggest.stream().filter(x->x.getText().toLowerCase().contains(destination.toLowerCase())).findFirst().get().click();
    }

    public FligtsResultsPage clickBuscar(){
        btnBuscar.click();
        webDriver.sleep(10);
        return new FligtsResultsPage(webDriver);
    }

    private void clickOnDate(){
        datePicker.findElement(By.xpath("div/div[5]/div["+calendarSupport.targetMonth+"]/div[4]/span["+calendarSupport.targetDay+"]")).click();
    }

    public void selectDates(String startDate, String endDate){
        if(calendarSupport.isRangeDateCorrect(startDate,endDate)){
            clickStartDateInput(startDate);
            webDriver.sleep(1);
            clickEndDateInput(endDate);
            webDriver.sleep(1);
        }
    }

    private void clickStartDateInput(String inputDate){
        startDateInput.click();
        webDriver.sleep(1);
        calendarSupport.setTargetDate(inputDate);
        for(int i = 0; i>calendarSupport.clicksToTargetMonth;i++){
            nextMonth.click();
            webDriver.sleep(1);}
        clickOnDate();
    }

    private void clickEndDateInput(String inputDate){
        final int clicksToTargetMonthByStartDate = calendarSupport.clicksToTargetMonth;
        calendarSupport.setTargetDate(inputDate);
        if(calendarSupport.clicksToTargetMonth > clicksToTargetMonthByStartDate){
            for(int i = 0; i<calendarSupport.clicksToTargetMonth;i++){
                nextMonth.click();
                webDriver.sleep(1);
            }
            clickOnDate();
        }
        if (calendarSupport.clicksToTargetMonth == clicksToTargetMonthByStartDate)
            clickOnDate();
    }

}
