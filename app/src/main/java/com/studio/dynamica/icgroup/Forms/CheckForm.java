package com.studio.dynamica.icgroup.Forms;

public class CheckForm {
    String id, created_at, consumption;
    int repair=0, replace=0, missing=0, moving=0, remainder=0;
    public CheckForm(String id, String consumption){
        this.id=id;this.consumption=consumption;
    }

    public String getId() {
        return id;
    }

    public String getConsumption() {
        return consumption;
    }

    public int getMissing() {
        return missing;
    }

    public int getMoving() {
        return moving;
    }

    public int getRemainder() {
        return remainder;
    }

    public int getRepair() {
        return repair;
    }

    public int getReplace() {
        return replace;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public void setReplace(int replace) {
        this.replace = replace;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setMissing(int missing) {
        this.missing = missing;
    }

    public void setMoving(int moving) {
        this.moving = moving;
    }

    public void setRemainder(int remainder) {
        this.remainder = remainder;
    }

    public void setRepair(int repair) {
        this.repair = repair;
    }

}
