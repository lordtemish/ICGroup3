package com.studio.dynamica.icgroup.Forms;

public class WorkScheduleForm {
    String dayType;
    String dayInfo;
    String smena1;
    String smena2;
    public WorkScheduleForm(String a, String b, String c, String d){
        dayType=a;
        dayInfo=b;
        smena1=c;
        smena2=d;
    }

    public String getDayInfo() {
        return dayInfo;
    }

    public String getDayType() {
        return dayType;
    }

    public String getSmena1() {
        return smena1;
    }

    public String getSmena2() {
        return smena2;
    }
}
