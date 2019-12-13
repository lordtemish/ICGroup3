package com.studio.crm.icgroup.Forms;

public class ServiceForm {
    String serviceType;
    String empl;
    String id;
    String position;
    String status;
    String priority;
    int day1;
    int day2;

    String day, time;
    public void setTime(String d, String t){
        day=d;time=t;
    }

    public String getTime() {
        return time;
    }

    public String getDay() {
            return day;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public ServiceForm(){}
    public ServiceForm(String s, String e,String position, String status, String p, int d1, int d2){
        serviceType=s;
        empl=e;
        this.position=position;
        this.status=status;
        priority=p;
        day1=d1;
        day2=d2;
    }

    public String getPosition() {
        return position;
    }

    public String getEmpl() {
        return empl;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getPriority() {
        return priority;
    }

    public int getDay1() {
        return day1;
    }

    public String getStatus() {
        return status;
    }

    public int getDay2() {
        return day2;
    }

    public void setEmpl(String empl) {
        this.empl = empl;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}

