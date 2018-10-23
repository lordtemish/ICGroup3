package com.studio.dynamica.icgroup.Forms;

import java.util.ArrayList;
import java.util.List;

public class AVRForm {
        String date, radio, workerName, workerPosition;
        int mark;
        List<MessageWorkForm> messageWorkForms;
        List<AcceptForm> acceptForms;
        public AVRForm(String da,int mar, String ra, String woNa, String woPo){
            mark=mar;
            date=da;
            radio=ra;
            workerName=woNa;
            workerPosition=woPo;
            messageWorkForms=new ArrayList<>();
            acceptForms=new ArrayList<>();
        }

    public void setAcceptForms(List<AcceptForm> acceptForms) {
        this.acceptForms = acceptForms;
    }

    public void setMessageWorkForms(List<MessageWorkForm> messageWorkForms) {
        this.messageWorkForms = messageWorkForms;
    }

    public String getDate() {
        return date;
    }

    public int getMark() {
        return mark;
    }

    public List<AcceptForm> getAcceptForms() {
        return acceptForms;
    }

    public List<MessageWorkForm> getMessageWorkForms() {
        return messageWorkForms;
    }

    public String getRadio() {
        return radio;
    }

    public String getWorkerName() {
        return workerName;
    }

    public String getWorkerPosition() {
        return workerPosition;
    }
}
