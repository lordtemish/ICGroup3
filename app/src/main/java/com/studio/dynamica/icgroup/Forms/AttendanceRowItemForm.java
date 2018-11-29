package com.studio.dynamica.icgroup.Forms;

public class AttendanceRowItemForm {
    private boolean nothing=false, plus=false, absent=false,ill=false,replace=false, half=false, third=false;
    String day;
    public AttendanceRowItemForm(String day){
        this.day=day;
        nothing=true;
    }

    public void setHalf(boolean half) {
        this.half = half;
    }

    public void setThird(boolean third) {
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
        this.replace = replace;
    }

    public boolean isReplace() {
        return replace;
    }

    public void setAbsent(boolean absent) {
        this.absent = absent;
    }

    public void setIll(boolean ill) {
        this.ill = ill;
    }

    public void setNothing(boolean nothing) {
        this.nothing = nothing;
    }

    public void setPlus(boolean plus) {
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
