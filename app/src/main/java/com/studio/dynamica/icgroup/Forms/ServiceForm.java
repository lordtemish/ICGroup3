package com.studio.dynamica.icgroup.Forms;

public class ServiceForm {
    String serviceType;
    String empl;
    String status;
    String priority;
    int day1;
    int day2;
    public ServiceForm(){}
    public ServiceForm(String s, String e, String status, String p, int d1, int d2){
        serviceType=s;
        empl=e;
        this.status=status;
        priority=p;
        day1=d1;
        day2=d2;
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

