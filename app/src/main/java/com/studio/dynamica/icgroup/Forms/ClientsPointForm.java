package com.studio.dynamica.icgroup.Forms;

public class ClientsPointForm {
    String id;
    String name;
    String avatar;
    String fullname;
    public ClientsPointForm(String i, String n, String f){
        id=i;name=n;fullname=f;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getFullname() {
            return fullname;
    }
}
