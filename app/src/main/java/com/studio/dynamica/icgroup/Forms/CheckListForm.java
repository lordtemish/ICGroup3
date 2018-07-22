package com.studio.dynamica.icgroup.Forms;

public class CheckListForm  {
    private String date;
    private String FIO;
    private int mark;
    public CheckListForm(String d, String f, int m){
        date=d;
        FIO=f;
        mark=m;
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
