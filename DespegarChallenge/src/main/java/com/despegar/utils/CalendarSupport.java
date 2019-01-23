package com.despegar.utils;

import org.testng.SkipException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


public class CalendarSupport {

    public static int targetDay = 0, targetMonth = 0, targetYear = 0;

    static int currentDay = 0, currentMonth = 0, currentYear = 0;

    public static int clicksToTargetMonth = 0;


    public Boolean isRangeDateCorrect(String startDate, String endDate){
        try {
            SimpleDateFormat formater = new SimpleDateFormat("d/MM/yyyy");
            Date startDateToCheck = formater.parse(startDate);
            Date endDateToCheck = formater.parse(endDate);
            return isDateMajor(startDateToCheck, endDateToCheck);
        }catch (ParseException e){
            throw new SkipException(e.getMessage());
        }
    }

    private Boolean isDateMajor(Date startDate, Date endDate){
        return startDate.before(endDate);
    }

    public void setTargetDate(String dateTarget){

        Calendar calendar = Calendar.getInstance();
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        currentYear = calendar.get(Calendar.YEAR);

        String[] splitDateEntry =  dateTarget.split("/");

        targetDay = Integer.parseInt(splitDateEntry[0]);
        targetMonth = Integer.parseInt(splitDateEntry[1]);
        targetYear = Integer.parseInt(splitDateEntry[2]);

        if(targetYear>currentYear){
            clicksToTargetMonth = (targetMonth - currentMonth) + (targetYear-currentYear)*12;
        }
        else {
            if(currentMonth == targetMonth){
                clicksToTargetMonth = 0;
            }

            if(targetMonth > currentMonth){
                clicksToTargetMonth = targetMonth - currentMonth;
            }
        }

        if(clicksToTargetMonth >12){
            throw new SkipException("Date out of range available");
        }
    }

    public String getDateNowAndAddDays(int days){
        LocalDate localDate = LocalDate.now().plusDays(days);
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("d/MM/yyyy");
        return localDate.format(formater);
    }

    public String getSystemDateNowAsString(){
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("d/MM/yyyy");
        return localDate.format(formater);
    }

}
