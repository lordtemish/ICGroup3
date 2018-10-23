package com.studio.dynamica.icgroup.Forms;

public class AttendanceRowItemForm {
    private boolean nothing=false, plus=false, absent=false,ill=false;
    int day;
    public AttendanceRowItemForm(int day){
        this.day=day;
        nothing=true;
    }
    public AttendanceRowItemForm(int day,boolean plus, boolean absent, boolean ill){
        this.day=day;
        nothing=false;
        this.plus=plus;
        this.absent=absent;
        this.ill=ill;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDay() {
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
