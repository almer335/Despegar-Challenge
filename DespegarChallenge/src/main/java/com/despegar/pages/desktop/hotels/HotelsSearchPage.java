package com.despegar.pages.desktop.hotels;

import com.despegar.pages.desktop.commons.Page;
import com.despegar.utils.CalendarSupport;
import com.despegar.webdriver.ExtendedWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HotelsSearchPage extends Page {

    @FindBy(xpath = "//div[starts-with(@class,'searchbox-sbox-box-hotels')]")
    private WebElement searchBox;

    @FindBy(className = "sbox-ui-datepicker-container")
    private WebElement datePicker;

    @FindBy(xpath = "//input[contains(@class,'sbox-destination') and @placeholder='Ingresá una ciudad, alojamiento o atracción']")
    private WebElement destinationInput;

    @FindBy(xpath = "//*[@class='_dpmg2--controls-next']//i")
    private WebElement nextMonth;

    @FindBy(xpath = "(//input[contains(@class,'sbox-checkin-date')])[2]")
    private WebElement startDateInput;

    @FindBy(xpath = "//input[contains(@class,'sbox-checkout-date')]")
    private WebElement endDateInput;

    @FindBy(xpath = "(//div[starts-with(@class,'sbox-distri-inputs-container')])[3]")
    private WebElement distriInputBox;

    @FindBy(xpath = "/html/body/div[2]/div/div[1]/div[2]/div[1]/div[2]/div[2]/div[2]/div/a[2]")
    private WebElement addMinor;

    @FindBy(xpath = "//a[contains(@class,'_pnlpk-apply-button') and text()='Aplicar']")
    private WebElement applyBtn;

    @FindBy(xpath = "(//em[@class='btn-text' and text()='Buscar'])[3]")
    private WebElement searchBtn;

    private static final Logger logger = LoggerFactory.getLogger(HotelsSearchPage.class);

    CalendarSupport calendarSupport = new CalendarSupport();

    public HotelsSearchPage(ExtendedWebDriver webDriver) {
        super(webDriver);
    }

    public void selectDestination(String destination){
        this.destinationInput.click();
        this.destinationInput.clear();
        this.destinationInput.sendKeys(destination);
        webDriver.sleep(2);
        List<WebElement> destinationAutoSuggest = webDriver.findElements(By.className("ac-container")).get(1).findElements(By.className("item"));
        destinationAutoSuggest.stream().filter(x->x.getText().toLowerCase().contains(destination.toLowerCase())).findFirst().get().click();
    }

    public void clickDistriBox(){
        distriInputBox.click();
    }

    public void addMinorByAge(int age){
        clickDistriBox();
        webDriver.sleep(1);
        addMinor.click();
        webDriver.sleep(1);
        Select setEdad = new Select(webDriver.findElement(By.xpath("//*[@class='select-container']//select")));
        setEdad.selectByValue(String.valueOf(age));
        webDriver.sleep(1);
        applyBtn.click();
        webDriver.sleep(1);
    }

    public HotelsResultsPage clickSearchBtn(){
        searchBtn.click();
        webDriver.sleep(8);
        return new HotelsResultsPage(webDriver);
    }

    private void clickOnDate(){
        datePicker.findElement(By.xpath("div/div[5]/div["+calendarSupport.targetMonth+"]/div[4]/span["+calendarSupport.targetDay+"]")).click();
    }

    public void selectDates(String startDate, String endDate){
        clickStartDateInput(startDate);
        webDriver.sleep(1);
        clickEndDateInput(endDate);
        webDriver.sleep(1);
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
