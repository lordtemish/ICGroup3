package com.studio.crm.icgroup.Forms;

import java.util.ArrayList;
import java.util.List;

public class InventoryStatusForm {
    List<CommentaryForm> commentaryForms;
    List<RemontForms> remontForms;
    String name, id, unit, vendor;
    int number;
    boolean status;

    public String getUnit() {
        return unit;
    }

    public InventoryStatusForm(String name , String id, int number){
        this.name=name;this.id=id;this.number=number;
        commentaryForms=new ArrayList<>();
        remontForms=new ArrayList<>();
        status=true;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setCommentaryForms(List<CommentaryForm> commentaryForms) {
        this.commentaryForms = commentaryForms;
    }

    public void setRemontForms(List<RemontForms> remontForms) {
        this.remontForms = remontForms;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<RemontForms> getRemontForms() {
        return remontForms;
    }

    public String getVendor() {
        return vendor;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public int getNumber() {
        return number;
    }

    public List<CommentaryForm> getCommentaryForms() {
        return commentaryForms;
    }
}
