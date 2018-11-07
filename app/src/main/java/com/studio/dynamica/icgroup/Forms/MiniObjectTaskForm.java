package com.studio.dynamica.icgroup.Forms;

public class MiniObjectTaskForm {
    String id, name;
    int rate, perfomance_rate;
    public MiniObjectTaskForm(String id, String name, int rate, int perfomance_rate){
        this.id=id;this.name=name;this.rate=rate;this.perfomance_rate=perfomance_rate;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getRate() {
        return rate;
    }

    public int getPerfomance_rate() {
        return perfomance_rate;
    }
}
