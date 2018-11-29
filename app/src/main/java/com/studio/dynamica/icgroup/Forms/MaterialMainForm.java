package com.studio.dynamica.icgroup.Forms;

import java.util.ArrayList;
import java.util.List;

public class MaterialMainForm {
    String name, id, vendor, unit;
    int whole=0, today=0;
    String wUnit, tUnit;
    List<MaterialMiniForm> forms;
    public MaterialMainForm(String idd, String namee, String vendorr, String unitt, int whol, int toda, String wU, String tU){
        name=namee;
        id=idd;
        vendor=vendorr;
        unit=unitt;
        whole=whol;
        today=toda;
        wUnit=wU;
        tUnit=tU;
        forms=new ArrayList<>();
    }

    public void setForms(List<MaterialMiniForm> forms) {
        this.forms.clear();
        this.forms.addAll(forms);
    }

    public String getwUnit() {
        return wUnit;
    }

    public String gettUnit() {
        return tUnit;
    }

    public int getWhole() {
        return whole;
    }

    public int getToday() {
        return today;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getVendor() {
        return vendor;
    }

    public String getUnit() {
        return unit;
    }

    public List<MaterialMiniForm> getForms() {
        return forms;
    }
}
