package com.studio.crm.icgroup.Forms;

public class svodkaRateForm {
    String id;
    private String date;
    private String name;
    private String position;
    private int rate;
    public svodkaRateForm(){
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
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
