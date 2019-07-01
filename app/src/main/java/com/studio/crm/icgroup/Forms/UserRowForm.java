package com.studio.crm.icgroup.Forms;

public class UserRowForm {
        String name;
        String position;
        String url="";
        String date;
        String typeLabel;
        String type;
        boolean full=false;
        public UserRowForm(String name, String position, String date, String tl, String t){
            this.name=name;
            this.position=position;
            this.date=date;
            typeLabel=tl;
            type=t;
            full=true;
        }
        public UserRowForm(String tl, String t){
            typeLabel=tl;
            type=t;
            full=false;
        }
        public UserRowForm(String na, String po, String da){
            name=na;position=po;date=da;
        }

    public boolean isFull() {
        return full;
    }

    public String getType() {
        return type;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }
}
