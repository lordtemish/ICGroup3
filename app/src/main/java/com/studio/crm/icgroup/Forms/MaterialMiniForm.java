package com.studio.crm.icgroup.Forms;

public class MaterialMiniForm {
    String name, id, wUnit="", tUnit="";
    int whole=0, today=0, progress=0;
    public MaterialMiniForm(String idd, String namee, int wh, int to, int pro){
        id=idd;
        name=namee;
        whole=wh;
        today=to;
        progress=pro;
    }

    public String gettUnit() {
        return tUnit;
    }

    public String getwUnit() {
        return wUnit;
    }

    public void settUnit(String tUnit) {
        this.tUnit = tUnit;
    }

    public void setwUnit(String wUnit) {
        this.wUnit = wUnit;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getProgress() {
        return progress;
    }

    public int getToday() {
        return today;
    }

    public int getWhole() {
        return whole;
    }
}
