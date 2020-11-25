package com.example.studentrelief.ui.misc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateFormatter {
    public static final String DATE_ON_LIST_PATTERN = "YYYY-M-d hh:mm:ss";
    public static final String DB_PATTERN = "yyyy-MM-dd hh:mm:ss";
    public static final String MMM_DD_YYYY = "MMM dd, yyyy";
    public  static final String convertToSimpleDateString(String dateString) throws ParseException {
        // must use the original pattern
        SimpleDateFormat formatter = new SimpleDateFormat(DB_PATTERN, Locale.ENGLISH);
        // try to convert the string date to date
        Date date = formatter.parse(dateString);
        // apply the wanted pattern
        formatter.applyPattern(MMM_DD_YYYY);
        // format it
        String formattedDateString = formatter.format(date);
        return formattedDateString;
    }
    public  static final String convertDatePattern(String dateString,String oldPattern, String newPattern) throws ParseException {
        // must use the original pattern
        SimpleDateFormat formatter = new SimpleDateFormat(oldPattern, Locale.ENGLISH);
        // try to convert the string date to date
        Date date = formatter.parse(dateString);
        // apply the wanted pattern
        formatter.applyPattern(newPattern);
        // format it
        String formattedDateString = formatter.format(date);
        return formattedDateString;
    }
}
