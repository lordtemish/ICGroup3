package com.studio.dynamica.icgroup.Forms;

import java.util.ArrayList;
import java.util.List;

public class EquipmentForm {
    String name, id;
    int num;
    List<RemontForms> remontForms;
    List<OrderForm> orderForms;
    public EquipmentForm(String name, String id, int num){
        this.name=name;
        this.id=id;
        this.num=num;
        remontForms=new ArrayList<>();orderForms=new ArrayList<>();
    }
    public EquipmentForm(String name , String id, int num, List<RemontForms> remontForms, List<OrderForm> orderForms){
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

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
