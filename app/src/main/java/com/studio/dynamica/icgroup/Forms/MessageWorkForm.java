package com.studio.dynamica.icgroup.Forms;

public class MessageWorkForm {
    boolean message=false;
    String name, info="";
    int num;
    public MessageWorkForm(String n, boolean m, int nun){
        name=n;
        message=m;
        num=nun;
    }
    public MessageWorkForm(String n, boolean m, int nun, String tex){
        name=n;
        message=m;
        num=nun;
        info=tex;
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    public String getInfo() {
        return info;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isMessage() {
        return message;
    }
}
