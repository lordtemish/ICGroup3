package com.studio.dynamica.icgroup.Forms;

public class CheckListBoxRowForm {
    private String name, info;
    private int rate;
    public CheckListBoxRowForm(String name, String info, int rate){
        this.name=name;
        this.info=info;
        this.rate=rate;
    }

    public int getRate() {
        return rate;
    }

    public String getInfo() {
        return info;
    }
    public String getName() {
        return name;
    }
}
