package com.studio.dynamica.icgroup.Forms;

public class CheckListBoxRowForm {
    private String name, info, id;
    private int rate;
    public CheckListBoxRowForm(String name, String info, int rate){
        this.name=name;
        this.info=info;
        this.rate=rate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getInfo() {
        return info;
    }
    public String getName() {
        return name;
    }
}
