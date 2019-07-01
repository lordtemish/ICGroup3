package com.studio.crm.icgroup.Forms;

import java.util.List;

public class MiniObjectForm {
    String name;
    boolean open;
    List<MiniObjectTaskForm> forms;
    public MiniObjectForm(String name, List<MiniObjectTaskForm> taskForms){
        this.name=name;forms=taskForms;open=false;
    }

    public void setForms(List<MiniObjectTaskForm> forms) {
        this.forms = forms;
    }
    public void addForm(MiniObjectTaskForm form) {
        forms.add(form);
    }

    public String getName() {
        return name;
    }

    public List<MiniObjectTaskForm> getForms() {
        return forms;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
