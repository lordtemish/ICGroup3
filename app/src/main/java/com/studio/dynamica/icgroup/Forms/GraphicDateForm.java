package com.studio.dynamica.icgroup.Forms;

import java.util.Calendar;

public class  GraphicDateForm {
    boolean clicked=false, today=false;
    int high=-1, low=-1, medium=-1;
    Calendar calendar;
    int day;
    String week;

    public int getDay() {
        return day;
    }

    public String getWeek() {
        return week;
    }

    public void setToday(boolean today) {
        this.today = today;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public void setMedium(int medium) {
        this.medium = medium;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public GraphicDateForm(){
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public  GraphicDateForm(boolean click, boolean segodnya, int highAF, int  medAF, int lowAF){
        clicked=click;
        today=segodnya;
        high=highAF;
        medium=medAF;
        low=lowAF;
    }

    public int getHigh() {
        return high;
    }

    public int getLow() {
        return low;
    }

    public int getMedium() {
        return medium;
    }

    public boolean isClicked() {
        return clicked;
    }

    public boolean isToday() {
        return today;
    }
}
