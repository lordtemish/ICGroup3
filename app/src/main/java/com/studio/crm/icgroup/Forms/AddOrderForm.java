package com.studio.crm.icgroup.Forms;

public class AddOrderForm {
    String date;
    String id, place, priority, status, type, mainstatus;
    int day1,day2;
    boolean mass=false;

    public void setMass(boolean mass) {
        this.mass = mass;
    }

    public boolean isMass() {
        return mass;
    }

    public AddOrderForm(String date, String id, String place, String priority, String status, String type, int day1, int day2){
        this.date=date;
        this.id=id;
        this.place=place;
        this.priority=priority;
        this.status=status;
        this.type=type;
        this.day1=day1;
        this.day2=day2;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getPriority() {
        return priority;
    }

    public String getPlace() {
        return place;
    }

    public String getType() {
        return type;
    }

    public int getDay1() {
        return day1;
    }

    public int getDay2() {
        return day2;
    }
}
