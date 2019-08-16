package com.studio.crm.icgroup.Forms;

import org.json.JSONObject;

public class ShiftForm {
    private String begin, end;
    private boolean weekend, is_night=false;
    int shift, day;
    public ShiftForm(JSONObject object){
        try {
            begin = object.getString("begin").substring(0,5);
            end = object.getString("end").substring(0,5);
            int day=object.getInt("day");
            is_night=object.getBoolean("is_night");
            weekend =day>5;// object.getBoolean("is_active");
            shift=day;//object.getInt("shift");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isIs_night() {
        return is_night;
    }

    public int getShift() {
        return shift;
    }

    public String getBegin() {
        return begin;
    }

    public String getEnd() {
        return end;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public boolean isWeekend() {
        return weekend;
    }
}
