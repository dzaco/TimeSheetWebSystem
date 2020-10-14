package com.epstein.model;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateInfo {

    private LocalDate now;
    private LocalDate firstDay;
    private LocalDate lastDay;
    private int offSet;

    public static String[] months = new String[] {
            "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj",
            "Czerwiec", "Lipiec", "Sierpień", "Wrzesień",
            "Październik", "Listopad", "Grudzień"
    };

    public DateInfo() {
        now = LocalDate.now();
        init();
    }
    public DateInfo(int year, int month, int day) {
        this.now = LocalDate.of(year, month, day);
        init();
    }
    public DateInfo(LocalDate date) {
        this.now = date;
        init();
    }
    private void init(){
        firstDay = now.minusDays( now.getDayOfMonth()-1 );
        lastDay =  LocalDate.of(now.getYear(), now.getMonth().plus(1), 1).minusDays(1);
        offSet = firstDay.getDayOfWeek().getValue()-2;
    }

    public boolean isWeekend(int day) {
        LocalDate thatDay = firstDay.plusDays(day-1);
        int val = thatDay.getDayOfWeek().getValue();
        return val == 6 || val == 7;
    }
    public boolean isWeekend(int week, int day) {
        return this.isWeekend( 7*week + day - this.offSet() );
    }

    public int getYear() {
        return this.now.getYear();
    }
    public Month getMonth() {
        return this.now.getMonth();
    }
    public String getMonthName() {
        String str = this.now.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault());
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }
    public String getMonthAndYear() {
        return this.getMonthName() + " " + this.getYear();
    }

    public String getPreviousMonthName() {
        return this.now.minusMonths(1).getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault());
    }
    public DateInfo getPreviousMonth() {
        return new DateInfo( now.minusMonths(1));
    }

    public int getDay() {
        return this.now.getDayOfMonth();
    }
    public String getDayName() {
        return this.now.getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault());
    }
    public int getDayOfWeek() {
        return this.now.getDayOfWeek().getValue();
    }
    public int getLastDay() {
        return lastDay.getDayOfMonth();
    }
    public int getFirstDay() {
        return firstDay.getDayOfMonth();
    }

    public int offSet() {
        return this.offSet;
    }

    public String getMonthNameOf(int month) {
        String str = Month.of(month).getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault());
        return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
    }


    public int getMonthValueOf(String month) {
        for(int i = 0; i < 12; i++) {
            if( month.equals(months[i])) {
                return ++i;
            }
        }
        return 0;
    }


}
