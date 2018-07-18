package com.studio.dynamica.icgroup.Forms;

public class MainFramentProgressForm {
    String name;
    int percentage;
    MainFramentProgressForm(){}
    public MainFramentProgressForm(String name, int per){
        this.name=name;
        this.percentage=per;
    }

    public int getPercentage() {
        return percentage;
    }

    public String getName() {
        return name;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public void setName(String name) {
        this.name = name;
    }
}
