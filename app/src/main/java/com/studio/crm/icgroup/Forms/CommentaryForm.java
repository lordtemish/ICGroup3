package com.studio.crm.icgroup.Forms;

public class CommentaryForm {
    String date;
    String name;
    String text;
    public CommentaryForm(String d, String n, String t){
        this.date=d;
        this.name=n;
        this.text=t;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
