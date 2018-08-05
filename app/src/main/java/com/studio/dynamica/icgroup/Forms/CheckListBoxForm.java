package com.studio.dynamica.icgroup.Forms;

import java.util.List;

public class CheckListBoxForm {
    String name;
    boolean accept;
    List<CheckListBoxRowForm> list;
    public CheckListBoxForm(String name, boolean accept, List<CheckListBoxRowForm> forms){
        this.name=name;
        this.accept=accept;
        this.list=forms;
    }

    public String getName() {
        return name;
    }

    public List<CheckListBoxRowForm> getList() {
        return list;
    }

    public boolean isAccept() {
        return accept;
    }
}
