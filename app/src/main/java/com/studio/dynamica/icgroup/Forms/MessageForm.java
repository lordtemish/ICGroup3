package com.studio.dynamica.icgroup.Forms;

public class MessageForm {
    String text;
    String date, FIO;
    int rate;
    boolean full;
    public MessageForm(String text){
        this.text=text;
        full=false;
    }
    public MessageForm(String text, String date, String F, int rate){
        this.text=text;this.date=date;FIO=F;this.rate=rate;
        full=true;
    }

    public boolean isFull() {
        return full;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public int getRate() {
        return rate;
    }

    public String getFIO() {
        return FIO;
    }
}

