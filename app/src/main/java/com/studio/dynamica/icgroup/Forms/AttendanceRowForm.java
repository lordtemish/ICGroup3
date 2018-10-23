package com.studio.dynamica.icgroup.Forms;

import android.view.View;

import java.util.List;

public class AttendanceRowForm {
    private List<AttendanceRowItemForm> rowForms;
    View.OnClickListener listener;
    private String name;
    private int n1, n2;
    boolean today;
    public AttendanceRowForm(String id,String n, List<AttendanceRowItemForm> itemForms, int num1, int num2){
        name=n;
        rowForms=itemForms;
        n1=num1;
        n2=num2;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public String getName() {
        return name;
    }

    public int getN1() {
        return n1;
    }

    public int getN2() {
        return n2;
    }

    public List<AttendanceRowItemForm> getRowForms() {
        return rowForms;
    }
}
