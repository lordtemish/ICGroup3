package com.studio.crm.icgroup.Forms;

import java.util.ArrayList;
import java.util.List;

public class WorkScheduleForm {
    String dayType;
    String dayInfo;
    List<ShiftForm> shiftForms;
    int shifts;
    public WorkScheduleForm(String a, String b, List<ShiftForm> shiftForms){
        this.shiftForms=new ArrayList<>();
        dayType=a;
        dayInfo=b;
        this.shiftForms.addAll(shiftForms);
        shifts=shiftForms.size();
    }
    public String getDayInfo() {
        return dayInfo;
    }

    public String getDayType() {
        return dayType;
    }

    public int getShifts() {
        return shifts;
    }

    public List<ShiftForm> getShiftForms() {
        return shiftForms;
    }
}
