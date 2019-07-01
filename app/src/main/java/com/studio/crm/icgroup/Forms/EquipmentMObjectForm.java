package com.studio.crm.icgroup.Forms;

public class EquipmentMObjectForm {
    int qua=0, perc=0;
    String name, id;
    String unit="";
    boolean whole=false;

    public void setWhole(boolean whole) {
        this.whole = whole;
    }

    public boolean isWhole() {
        return whole;
    }

    public EquipmentMObjectForm(String idd, String namee, String unitt, int quaa, int percc){
        id=idd;
        name=namee;
        unit=unitt;
        qua=quaa;
        perc=percc;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPerc() {
        return perc;
    }

    public int getQua() {
        return qua;
    }

    public String getUnit() {
        return unit;
    }
}
