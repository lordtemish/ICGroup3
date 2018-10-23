package com.studio.dynamica.icgroup.Forms;

public class RowFormPTN {
    int drawable, drawable1;
    String name;
    int num;
    boolean clicked=false;
    public RowFormPTN(){}
    public RowFormPTN(int drawable,int drawable1, String name, int num){
        this.drawable=drawable;
        this.drawable1=drawable1;
        this.name=name;
        this.num=num;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public int getDrawable() {
        return drawable;
    }

    public int getDrawable1() {
        return drawable1;
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
