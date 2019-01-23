package com.despegar.desktop.fligths;

import com.despegar.core.TestBaseFrontEnd;
import com.despegar.pages.desktop.flights.FlightsConfirmationPage;
import com.despegar.pages.desktop.flights.FlightsSearchPage;
import com.despegar.pages.desktop.flights.FligtsResultsPage;
import com.despegar.utils.DataProvidersClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;


import static org.testng.Assert.assertTrue;

public class flightsTest extends TestBaseFrontEnd {

    private static final Logger LOGGER = LoggerFactory.getLogger(flightsTest.class);

    @Test(dataProvider = "dataFlightsFromJson", dataProviderClass = DataProvidersClass.class)
    public void dadoLosParametrosParaCompletareFormularioDeVueloCuandoSeHaceClickEnbuscarEntoncesLaPaginaDevuelveListadoDeResultados(String originCity , String destinationCity, String startDate, String endDate ){
        //Given
        LOGGER.info("START-TEST");
        FlightsSearchPage flightsSearchPage = eCommerceSiteMap.homePage().goToFlights();
        flightsSearchPage.selectOrigin(originCity);
        flightsSearchPage.selectDestination(destinationCity);
        flightsSearchPage.selectDates(startDate,endDate);
        //When
        FligtsResultsPage fligtsResultsPage = flightsSearchPage.clickBuscar();
        //Then
        assertTrue(fligtsResultsPage.hasResults());
        LOGGER.info("END-TEST");
    }

    @Test(dataProvider = "dataFlightsFromJson", dataProviderClass = DataProvidersClass.class)
    public void dadoLosResultadosDeVuelosCuandoSeHaceClickEnElDeMayorPrecioEntoncesSeMuestraLaPaginaParaCompletarLaSolicitud(String originCity , String destinationCity, String startDate, String endDate){
        //Given
        LOGGER.info("START-TEST");
        FlightsSearchPage flightsSearchPage = eCommerceSiteMap.homePage().goToFlights();
        flightsSearchPage.selectOrigin(originCity);
        flightsSearchPage.selectDestination(destinationCity);
        flightsSearchPage.selectDates(startDate,endDate);
        FligtsResultsPage fligtsResultsPage = flightsSearchPage.clickBuscar();
        assertTrue(fligtsResultsPage.hasResults());
        //When
        fligtsResultsPage.selectHighestAirlinesFromTable();
        FlightsConfirmationPage flightsConfirmationPage = fligtsResultsPage.selectHighestCostFlight();
        //Then
        assertTrue(flightsConfirmationPage.isContactInfoLabelDisplayed());
        assertTrue(flightsConfirmationPage.isInvoiceAdresLabelDisplayed());
        assertTrue(flightsConfirmationPage.isPaymentMethodFrameDisplayed());
        LOGGER.info("END-TEST");
    }

}
