package com.studio.crm.icgroup.Forms;

public class CommentForm {
    String id;
    String sender;
    String date;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public CommentForm(String sender, String date){
        this.sender=sender;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public String getSender() {
        return sender;
    }
}
