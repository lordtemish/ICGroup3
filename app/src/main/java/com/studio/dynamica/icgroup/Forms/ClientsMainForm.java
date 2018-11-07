package com.studio.dynamica.icgroup.Forms;

import java.util.List;

public class ClientsMainForm {
    String id;
    String name;
    String fullname;
    String avatar;
    int rate;
    List<ClientsPointForm> pointForms;
    public ClientsMainForm(String i, String n, String f, int r){
        id=i;name=n;fullname=f;rate=r;
    }

    public int getRate() {
        return rate;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setPointForms(List<ClientsPointForm> pointForms) {
        this.pointForms = pointForms;
    }

    public String getFullname() {
        return fullname;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<ClientsPointForm> getPointForms() {
        return pointForms;
    }
}
