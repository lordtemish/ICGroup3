package com.studio.crm.icgroup.Forms;

public class PhonesRowForm {
    boolean type;
    String name;
    String position;
    String phone="";
    public  PhonesRowForm(){}
    public PhonesRowForm(boolean type, String name, String position){
        this.type=type;
        this.name=name;
        this.position=position;
    }
    public PhonesRowForm(boolean type, String name, String position, String phone){
        this.type=type;
        this.name=name;
        this.position=position;
        this.phone=phone;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public boolean isType() {
        return type;
    }
}
