package com.studio.crm.icgroup.Forms;

import java.util.List;

public class InventoryStatusFactoryForm {
    String text;
    boolean updated=false;

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public boolean isUpdated() {
        return updated;
    }

    List<InventoryStatusForm> statusForms;
    String key;
    public InventoryStatusFactoryForm(String t,List<InventoryStatusForm> statusForms, String key){
        this.text=t;
        this.statusForms=statusForms;
        this.key=key;
    }

    public String getKey() {
        return key;
    }

    public void setStatusForms(List<InventoryStatusForm> statusForms) {
        this.statusForms = statusForms;
    }

    public String getText() {
        return text;
    }

    public List<InventoryStatusForm> getStatusForms() {
        return statusForms;
    }
}
