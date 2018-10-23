package com.studio.dynamica.icgroup.Forms;

import java.util.List;

public class InventorizationPassingSetForm {
    private String name, id;
    private int number;
    private boolean status=true;
    private ExtraInfo info;
    public InventorizationPassingSetForm(String n, String i, int num){
            number=num;name=n;
            id=i;
            status=true;
            info=new ExtraInfo();
    }
    public InventorizationPassingSetForm(String n, String i, int num, int page, int pnum){
        number=num;name=n;
        id=i;
        status=false;
        info=new ExtraInfo(page,pnum, null);
    }

    public ExtraInfo getInfo() {
        return info;
    }

    public int getNumber() {
        return number;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInfo(ExtraInfo info) {
        this.info = info;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public class ExtraInfo{
        int page, number;
        CommentaryForm commentForm;
        private ExtraInfo(int p, int n,CommentaryForm c){
            page=p;number=n;commentForm=c;
        }
        private ExtraInfo(){
            page=0;number=0;
        }

        public int getNumber() {
            return number;
        }

        public CommentaryForm getCommentForm() {
            return commentForm;
        }

        public int getPage() {
            return page;
        }

        public void setCommentForm(CommentaryForm commentForm) {
            this.commentForm = commentForm;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public void setPage(int page) {
            this.page = page;
        }
    }
}
