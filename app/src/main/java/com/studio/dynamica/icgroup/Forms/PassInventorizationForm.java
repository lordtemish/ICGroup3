package com.studio.dynamica.icgroup.Forms;

public class PassInventorizationForm {
    String text;
    boolean status;
    int perc;
    public PassInventorizationForm(String t, boolean s, int p){
        text=t;
        status=s;
        perc=p;
    }

    public String getText() {
        return text;
    }
    public int getPerc() {
        return perc;
    }
    public boolean isStatus() {
        return status;
    }
}