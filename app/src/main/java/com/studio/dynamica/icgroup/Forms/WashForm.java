package com.studio.dynamica.icgroup.Forms;

public class WashForm {
    String name;
    String id;
    int n;
    public WashForm(String name, String id , int number){
        this.name=name;
        this.id=id;
        this.n=number;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getNumbers() {
        return n;
    }
}
