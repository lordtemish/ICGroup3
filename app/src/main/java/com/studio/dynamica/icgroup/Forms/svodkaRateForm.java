package com.studio.dynamica.icgroup.Forms;

public class svodkaRateForm {
    private String date;
    private String name;
    private String position;
    private int rate;
    public svodkaRateForm(){
    }
    public svodkaRateForm(String d, String p, String n, int r){
        date=d;
        position=p;
        name=n;
        rate=r;
    }

    public int getRate() {
        return rate;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }
}
