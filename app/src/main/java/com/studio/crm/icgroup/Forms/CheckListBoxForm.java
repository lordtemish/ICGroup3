package com.studio.crm.icgroup.Forms;

import java.util.List;

public class CheckListBoxForm {
    String name, id="";
    boolean accept, open=false;
    List<CheckListBoxRowForm> list;
    public CheckListBoxForm( String name, boolean accept, List<CheckListBoxRowForm> forms){
        this.name=name;
        this.accept=accept;this.id=id;
        this.list=forms;
        open=false;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
