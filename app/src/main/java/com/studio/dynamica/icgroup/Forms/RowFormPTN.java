package com.studio.dynamica.icgroup.Forms;

public class RowFormPTN {
    int drawable;
    String name;
    int num;
    public RowFormPTN(){}
    public RowFormPTN(int drawable, String name, int num){
        this.drawable=drawable;
        this.name=name;
        this.num=num;
    }

    public int getDrawable() {
        return drawable;
    }

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
