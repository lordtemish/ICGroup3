package com.studio.dynamica.icgroup.Forms;

public class NotificationForm {
    String type;
    String date;
    String name;
    String autorname;
    String autorposition;
    public NotificationForm(String t, String d, String n, String an, String ap){
        type=t;date=d;name=n;autorname=an;autorposition=ap;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getAutorname() {
        return autorname;
    }

    public String getAutorposition() {
        return autorposition;
    }
}
