package com.studio.dynamica.icgroup.Forms;

public class CheckListForm  {
    private String date;
    private String FIO;
    private int mark;
    String id;
    public CheckListForm(String d, String f, int m){
        date=d;
        FIO=f;
        mark=m;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getMark() {
        return mark;
    }

    public String getDate() {
        return date;
    }

    public String getFIO() {
        return FIO;
    }
}
