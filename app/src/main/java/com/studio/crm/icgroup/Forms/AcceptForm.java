package com.studio.crm.icgroup.Forms;

public class AcceptForm {
    String name, place, position, status;
    boolean green;
    public AcceptForm(String n, String p, String position, String st, boolean g){
        name=n;place=p;this.position=position;status=st;green=g;
    }

    public String getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public String getStatus() {
        return status;
    }

    public boolean isGreen() {
        return green;
    }
}
