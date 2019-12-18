package com.studio.crm.icgroup.Forms;

import android.view.View;

import java.util.List;

public class AttendanceRowForm {
    private List<AttendanceRowItemForm> rowForms;
    View.OnClickListener listener;
    private String name;
    private int n1, n2;
    boolean today, contract;
    String id, kind="";

    public boolean isContract() {
        return contract;
    }

    public void setContract(boolean contract) {
        this.contract = contract;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getKind() {
        return kind;
    }

    boolean fuck=false, text=false;
    public AttendanceRowForm(String id,String n, List<AttendanceRowItemForm> itemForms, int num1, int num2){
        this.id=id;
        name=n;
        rowForms=itemForms;
        n1=num1;
        n2=num2;
        text=false;
    }
    public AttendanceRowForm(String name){
        this.name=name;
        text=true;
    }

    public boolean isText() {
        return text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setToday(boolean today) {
        this.today = today;
    }

    public void setN1(int n1) {
        this.n1 = n1;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }

    public void setRowForms(List<AttendanceRowItemForm> rowForms) {
        this.rowForms = rowForms;
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

    public void setFucked(boolean fuck) {
        this.fuck = fuck;
    }

    public boolean isFuck() {
        return fuck;
    }
}
