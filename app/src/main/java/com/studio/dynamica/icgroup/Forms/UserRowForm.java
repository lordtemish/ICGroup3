package com.studio.dynamica.icgroup.Forms;

public class UserRowForm {
        String name;
        String position;
        String url="";
        String date;
        public UserRowForm(String name, String position, String date){
            this.name=name;
            this.position=position;
            this.date=date;
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
