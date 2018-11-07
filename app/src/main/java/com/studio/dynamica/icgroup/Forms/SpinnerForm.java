package com.studio.dynamica.icgroup.Forms;


public class SpinnerForm {
    String id, name;
    int num;
    public SpinnerForm(String i, String n){
        id=i;name=n;
        num=1;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
