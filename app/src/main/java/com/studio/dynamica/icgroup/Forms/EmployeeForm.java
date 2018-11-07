package com.studio.dynamica.icgroup.Forms;

public class EmployeeForm {
    String id, name;
    int empls, result_rate;
    public EmployeeForm(String id,String name,  int empls,int result_rate){
        this.id=id;this.name=name;
        this.empls=empls;this.result_rate=result_rate;
    }

    public String getId() {
        return id;
    }

    public int getResult_rate() {
        return result_rate;
    }

    public String getName() {
        return name;
    }

    public int getEmpls() {
        return empls;
    }
}
