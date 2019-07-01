package com.studio.crm.icgroup.Forms;

import android.view.View;

public class ChooseAcceptForm {
    boolean chose;
    String place, name, position;
    boolean client;
    View.OnClickListener listener;
    boolean listen=false;

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
        listen=true;
    }

    public boolean isListen() {
        return listen;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public ChooseAcceptForm(String place, String name, String position){
        this.place=place;
        this.name=name;
        this.position=position;
        chose=false;
        client=false;
    }
    public ChooseAcceptForm(String place, String name, String position, boolean c){
        this.place=place;
        this.name=name;
        this.position=position;
        chose=false;
        client=c;
    }

    public void setClient(boolean client) {
        this.client = client;
    }

    public boolean isClient() {
        return client;
    }

    public boolean isChose() {
        return chose;
    }

    public void setChose(boolean chose) {
        this.chose = chose;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public String getPosition() {
        return position;
    }
}
