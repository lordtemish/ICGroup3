package com.studio.dynamica.icgroup.Forms;

public class ChooseAcceptForm {
    boolean chose;
    String place, name, position;
    public ChooseAcceptForm(String place, String name, String position){
        this.place=place;
        this.name=name;
        this.position=position;
        chose=false;
    }

    public boolean isChose() {
        return chose;
    }

    public void setChose(boolean chose) {
        this.chose = chose;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public String getPosition() {
        return position;
    }
}
