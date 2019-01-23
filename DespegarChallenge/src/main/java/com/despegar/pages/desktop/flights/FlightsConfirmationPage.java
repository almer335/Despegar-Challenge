package com.despegar.pages.desktop.flights;

import com.despegar.pages.desktop.commons.Page;
import com.despegar.webdriver.ExtendedWebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlightsConfirmationPage extends Page {

    @FindBy( css = ".frame.travelers")
    private WebElement travelersFrame;

    @FindBy( css = ".frame.payment-method.with-legal-conditions")
    private WebElement paymentMethodFrame;

    @FindBy( xpath = "//div[contains(@class,'frame contact-info')]/div[contains(text(),'¿A dónde enviamos tus vouchers?')]")
    private WebElement invoiceAdresLabel;

    @FindBy( xpath = "//div[contains(@class,'frame contact-info')]/div[contains(text(),'¿A qué número podemos llamarte?')]")
    private WebElement contactInfoLabel;

    private static final Logger logger = LoggerFactory.getLogger(FlightsConfirmationPage.class);

    protected FlightsConfirmationPage(ExtendedWebDriver webDriver) {
        super(webDriver);
    }

    public Boolean isTravelersFrameDisplayed() {
        return travelersFrame.isDisplayed();
    }

    public Boolean isPaymentMethodFrameDisplayed() {
        return paymentMethodFrame.isDisplayed();
    }

    public Boolean isInvoiceAdresLabelDisplayed() {
        return invoiceAdresLabel.isDisplayed();
    }

    public Boolean isContactInfoLabelDisplayed() {
        return contactInfoLabel.isDisplayed();
    }
}
