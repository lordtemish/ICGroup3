package com.studio.dynamica.icgroup.Forms;

import org.json.JSONObject;

public class ShiftForm {
    private String begin, end;
    private boolean weekend;
    int shift;
    public ShiftForm(JSONObject object){
        try {
            begin = object.getString("begin").substring(0,5);
            end = object.getString("end").substring(0,5);
            weekend = object.getBoolean("is_weekend");
            shift=object.getInt("shift");
        }
        catch (Exception e){
            e.printStackTrace();
        }
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
