package com.studio.dynamica.icgroup.Forms;

public class RadioUserForm {
    public String name;
    public String position;
    public boolean checked;
    public RadioUserForm(String namee, String positionn){
        name=namee;position=positionn;checked=false;
    }
    public RadioUserForm(String namee, String positionn, boolean checkedd){
        name=namee;position=positionn;checked=checkedd;
    }
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }
}
