package com.studio.dynamica.icgroup.Forms;

public class ClientsPointForm {
    String id;
    String name;
    String avatar;
    int rate=0;

    public ClientsPointForm(String i, String n, int rate) {
        id = i;
        name = n;
        this.rate=rate;
    }

    public int getRate() {
        return rate;
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

}
