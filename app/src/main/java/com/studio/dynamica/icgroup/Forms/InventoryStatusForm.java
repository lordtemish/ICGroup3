package com.studio.dynamica.icgroup.Forms;

import java.util.ArrayList;
import java.util.List;

public class InventoryStatusForm {
    List<CommentaryForm> commentaryForms;
    List<RemontForms> remontForms;
    String name, id;
    int number;
    boolean status;
    public InventoryStatusForm(String name ,String id, int number){
        this.name=name;this.id=id;this.number=number;
        commentaryForms=new ArrayList<>();
        remontForms=new ArrayList<>();
        status=true;
    }

    public void setCommentaryForms(List<CommentaryForm> commentaryForms) {
        this.commentaryForms = commentaryForms;
    }

    public void setRemontForms(List<RemontForms> remontForms) {
        this.remontForms = remontForms;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<RemontForms> getRemontForms() {
        return remontForms;
    }

    public int getNumber() {
        return number;
    }

    public List<CommentaryForm> getCommentaryForms() {
        return commentaryForms;
    }
}
