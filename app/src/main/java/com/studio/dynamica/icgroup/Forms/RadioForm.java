package com.studio.dynamica.icgroup.Forms;

public class RadioForm {
    boolean status, idAble=false;
    String text, id="";
    public RadioForm(boolean s, String n){
        status=s;text=n;
    }

    public void setId(String id) {
        this.id = id;
        idAble=false;
    }

    public boolean isIdAble() {
        return idAble;
    }

    public String getId() {
        return id;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public boolean isStatus() {
        return status;
    }
}
