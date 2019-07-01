package com.studio.crm.icgroup.Forms;

import java.util.ArrayList;
import java.util.List;

public class EquipmentMainForm {
   private String id, name, vendor, unit;
    private int qua, replace=0,repair=0;


    public void setRepair(int repair) {
        this.repair = repair;
    }
    public void addRepair(int n){
        repair+=n;
    }

    public void setReplace(int replace) {
        this.replace = replace;
    }

    public void addReplace(int n) {
        this.replace +=n;
    }

    public int getReplace() {
        return replace;
    }

    public int getRepair() {
        return repair;
    }

  private   List<EquipmentMObjectForm>mObjectForms;
    public EquipmentMainForm(String idd, String namee, String unitt, int quaa){
        id=idd;name=namee;unit=unitt;
        qua=quaa;
        mObjectForms=new ArrayList<>();
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setmObjectForms(List<EquipmentMObjectForm> mObjectForms) {
        this.mObjectForms = mObjectForms;
    }

    public String getUnit() {
        return unit;
    }

    public int getQua() {
        return qua;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<EquipmentMObjectForm> getmObjectForms() {
        return mObjectForms;
    }

    public String getVendor() {
        return vendor;
    }
}
