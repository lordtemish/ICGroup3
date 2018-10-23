package com.studio.dynamica.icgroup.Forms;

public class ProgressPhoneForm {
    PhonesRowForm form;
    int progress;
    boolean change;
    String text, id;
    PhonesRowForm changeForm;
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
