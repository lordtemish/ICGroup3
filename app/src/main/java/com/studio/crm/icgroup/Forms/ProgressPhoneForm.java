package com.studio.crm.icgroup.Forms;

public class ProgressPhoneForm {
    PhonesRowForm form;
    int progress, salary=0;
    boolean change, contract=false;
    String text, id, userid="", status, role="";
    PhonesRowForm changeForm;

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    public boolean isContract() {
        return contract;
    }

    public void setContract(boolean contract) {
        this.contract = contract;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public ProgressPhoneForm(PhonesRowForm form, int progress){
        this.form=form;
        this.progress=progress;
        change=false;
    }
    public ProgressPhoneForm(PhonesRowForm form, int progress, String text, PhonesRowForm form2){
        this.form=form;
        this.progress=progress;
        this.text=text;
        this.changeForm=form2;
        change=true;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getProgress() {
        return progress;
    }

    public PhonesRowForm getChangeForm() {
        return changeForm;
    }

    public PhonesRowForm getForm() {
        return form;
    }

    public String getText() {
        return text;
    }

    public boolean isChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }

    public void setChangeForm(PhonesRowForm changeForm) {
        this.changeForm = changeForm;
    }

    public void setForm(PhonesRowForm form) {
        this.form = form;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setText(String text) {
        this.text = text;
    }
}
