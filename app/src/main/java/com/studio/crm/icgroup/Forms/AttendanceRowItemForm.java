package com.studio.crm.icgroup.Forms;

public class AttendanceRowItemForm {
    private boolean nothing=false, plus=false, absent=false,ill=false,replace=false, half=false, third=false, holiday=false;
    String day;
    public AttendanceRowItemForm(String day){
        this.day=day;
        nothing=true;
    }
    public void setAll(){
        nothing=false;plus=false;absent=false;ill=false;replace=false;half=false;third=false;
        holiday=false;
    }
    public void setHalf(boolean half) {
        nothing=false;
        this.half = half;
    }

    public void setHoliday(boolean holiday) {
        this.holiday = holiday;
    }

    public boolean isHoliday() {
        return holiday;
    }

    public void setThird(boolean third) {
        nothing=false;
        this.third = third;
    }

    public boolean isHalf() {
        return half;
    }

    public boolean isThird() {
        return third;
    }

    public AttendanceRowItemForm(String day, boolean plus, boolean absent, boolean ill){
        this.day=day;
        nothing=false;
        this.plus=plus;
        this.absent=absent;
        this.ill=ill;
    }

    public void setReplace(boolean replace) {
        nothing=false;
        this.replace = replace;
    }

    public boolean isReplace() {
        return replace;
    }

    public void setAbsent(boolean absent) {
        nothing=false;
        this.absent = absent;
    }

    public void setIll(boolean ill) {
        nothing=false;
        this.ill = ill;
    }

    public void setNothing(boolean nothing) {
        this.nothing = nothing;
    }

    public void setPlus(boolean plus) {
        nothing=false;
        this.plus = plus;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public boolean isAbsent() {
        return absent;
    }

    public boolean isIll() {
        return ill;
    }

    public boolean isNothing() {
        return nothing;
    }

    public boolean isPlus() {
        return plus;
    }
}
