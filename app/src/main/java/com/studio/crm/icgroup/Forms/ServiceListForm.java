package com.studio.crm.icgroup.Forms;

import java.util.List;

public class ServiceListForm {
    String name;
    List<ServicePeriodForm> forms;
    public ServiceListForm(String name, List<ServicePeriodForm> forms){
        this.name=name;this.forms=forms;
    }

    public String getName() {
        return name;
    }

    public List<ServicePeriodForm> getForms() {
        return forms;
    }
}
