package com.studio.dynamica.icgroup.Forms;

public class InventorizationForm {
    String date;
    String name;
    String position;
    int percentage;
    public InventorizationForm(String d, String n, String p, int perc){
        date=d;name=n;position=p;percentage=perc;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getPosition() {
        return position;
    }

    public int getPercentage() {
        return percentage;
    }
}
