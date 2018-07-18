package com.studio.dynamica.icgroup.Forms;

public class OlkForm {
    String date;
    String name;
    String position;
    public OlkForm(String d, String n, String p){
        date=d;
        name=n;
        position=p;
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
