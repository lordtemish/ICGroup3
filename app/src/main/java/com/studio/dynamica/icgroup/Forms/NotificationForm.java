package com.studio.dynamica.icgroup.Forms;

public class NotificationForm {
    String type;
    String date;
    String name;
    String autorname;
    String autorposition;
    String id="";
    String objectType="";
    boolean is_archive=false;

    public void setIs_archive(boolean is_archive) {
        this.is_archive = is_archive;
    }

    public boolean isIs_archive() {
        return is_archive;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAutorname(String autorname) {
        this.autorname = autorname;
    }

    public void setAutorposition(String autorposition) {
        this.autorposition = autorposition;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getObjectType() {
        return objectType;
    }

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
