package com.studio.dynamica.icgroup.Forms;

public class OlkForm  {
    private String date;
    private String name;
    private String position;
    private int mark;
    public OlkForm(String d, String n, String p,int  m){
        date=d;
        name=n;
        position=p;
        mark=m;
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

    public int getMark() {
        return mark;
    }
}
