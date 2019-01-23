package com.despegar.desktop.hotels;

import com.despegar.core.TestBaseFrontEnd;
import com.despegar.pages.desktop.hotels.HotelsProductViewPage;
import com.despegar.pages.desktop.hotels.HotelsResultsPage;
import com.despegar.pages.desktop.hotels.HotelsSearchPage;
import com.despegar.utils.CalendarSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class HotelsTest extends TestBaseFrontEnd {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelsTest.class);

    @Test
    public void dadoUnaCiudad2AdultosYUnMenorDe12AñosCuandoSeHaceunaBusquedadYSeFiltraPorHabitaciones5EstrellasEntoncesAlSeleccionarElMasBaratoSeAbreUnaNuevaPestañaConLasHabitacionesDisponibles(){
        //Given
        LOGGER.info("START-TEST");
        HotelsSearchPage hotelsSearchPage = eCommerceSiteMap.homePage().goToHotels();
        CalendarSupport calendarSupport = new CalendarSupport();
        hotelsSearchPage.selectDestination("montevideo");
        hotelsSearchPage.selectDates(calendarSupport.getDateNowAndAddDays(10),calendarSupport.getDateNowAndAddDays(13));
        hotelsSearchPage.addMinorByAge(12);
        //When
        HotelsResultsPage hotelsResultsPage = hotelsSearchPage.clickSearchBtn();
        hotelsResultsPage.setFiveStarsFilter();
        //Then
        HotelsProductViewPage hotelsProductViewPage = hotelsResultsPage.selectLowestCostHotel();
        assertTrue(hotelsProductViewPage.isSeeRoomsDisplayed());
        LOGGER.info("END-TEST");
    }
}
