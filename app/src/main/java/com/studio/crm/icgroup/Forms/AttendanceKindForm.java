package com.studio.crm.icgroup.Forms;

import java.util.List;

public class AttendanceKindForm {
    String name="";
    List<AttendanceRowForm> rowForms;
    public AttendanceKindForm(String name, List<AttendanceRowForm> forms){
        this.name=name;rowForms=forms;
    }

    public String getName() {
        return name;
    }

    public List<AttendanceRowForm> getRowForms() {
        return rowForms;
    }
    public int getSize(){
        return 1+rowForms.size();
    }
}
