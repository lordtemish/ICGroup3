package com.studio.crm.icgroup.Forms;

import java.util.ArrayList;
import java.util.List;

public class EquipmentForm {
    int replace=0, repair=0, limit=0;
    String name, id;
    String vendor_code="";
    String num;
    List<RemontForms> remontForms;
    List<OrderForm> orderForms;

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public void setVendor_code(String vendor_code) {
        this.vendor_code = vendor_code;
    }

    public String getVendor_code() {
        return vendor_code;
    }

    public EquipmentForm(String name, String id, String num){
        this.name=name;
        this.id=id;
        this.num=num;
        remontForms=new ArrayList<>();orderForms=new ArrayList<>();
    }
        public EquipmentForm(String name , String id, String num, List<RemontForms> remontForms, List<OrderForm> orderForms){
        this.name=name;
        this.id=id;
        this.num=num;
        this.remontForms=remontForms;
        this.orderForms=orderForms;
    }

    public List<OrderForm> getOrderForms() {
        return orderForms;
    }

    public List<RemontForms> getRemontForms() {
        return remontForms;
    }

    public String getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
