package com.studio.dynamica.icgroup.Forms;

public class CommentForm {
    String id;
    String sender;
    String date;
    public CommentForm(String sender,String date){
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
