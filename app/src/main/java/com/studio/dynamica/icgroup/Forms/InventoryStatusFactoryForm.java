package com.studio.dynamica.icgroup.Forms;

import java.util.List;

public class InventoryStatusFactoryForm {
    String text;
    List<InventoryStatusForm> statusForms;
    public InventoryStatusFactoryForm(String t,List<InventoryStatusForm> statusForms){
        this.text=t;
        this.statusForms=statusForms;
    }

    public String getText() {
        return text;
    }

    public List<InventoryStatusForm> getStatusForms() {
        return statusForms;
    }
}
