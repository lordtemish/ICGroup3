package com.studio.dynamica.icgroup.Forms;

import java.util.List;

public class InventorizationPassingSetForm {
    private String name, id, vendor, check="", consumption, comment="";
    private int number, repl=0, repa=0, miss=0, rema=0;
    private boolean status=true, created=false;
    private ExtraInfo info;
    public  void obNul(){
        created=false;status=true;
        repl=0;repa=0;miss=0;rema=0;check="";
    }
    public int getRema() {
        return rema;
    }

    public void setRema(int rema) {
        this.rema = rema;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public boolean isCreated() {
        return created;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }



    public void setMiss(int miss) {
        this.miss = miss;
    }

    public void setRepa(int repa) {
        this.repa = repa;
    }

    public void setRepl(int repl) {
        this.repl = repl;
    }

    public int getMiss() {
        return miss;
    }

    public int getRepa() {
        return repa;
    }

    public int getRepl() {
        return repl;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getCheck() {
        return check;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }


    public String getVendor() {
        return vendor;
    }

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
