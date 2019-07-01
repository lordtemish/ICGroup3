package com.studio.crm.icgroup.Forms;

public class PassInventorizationForm {
    String text;
    boolean status;
    int perc;
    String kind;
    public PassInventorizationForm(String t, boolean s, int p){
        text=t;
        status=s;
        perc=p;
    }
    public PassInventorizationForm(String t, boolean s, int p, String k){
        text=t;
        status=s;
        perc=p;
        kind=k;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setPerc(int perc) {
        this.perc = perc;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getKind() {
        return kind;
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