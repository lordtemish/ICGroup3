package com.studio.dynamica.icgroup.Forms;

import org.json.JSONObject;

public class ShiftForm {
    private String begin, end;
    private boolean weekend;
    int shift, day;
    public ShiftForm(JSONObject object){
        try {
            begin = object.getString("begin").substring(0,5);
            end = object.getString("end").substring(0,5);
            int day=object.getInt("day");
            weekend =day>0;// object.getBoolean("is_active");
            shift=1;//object.getInt("shift");
            if(weekend) shift=day;
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
