package com.studio.dynamica.icgroup.Forms;


public class SpinnerForm {
    String id, name, text="";
    int num;
    public SpinnerForm(String i, String n){
        id=i;name=n;
        num=1;
    }
    public SpinnerForm(){
        id="";name="";num=1;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
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
