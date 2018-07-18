package com.studio.dynamica.icgroup.Forms;

import java.util.List;

public class ServicePeriodForm {
    String name;
    List<String> list;
    public  ServicePeriodForm(String name, List<String> list){
        this.name=name;
        this.list=list;
    }

    public String getName() {
        return name;
    }

    public List<String> getList() {
        return list;
    }
}
