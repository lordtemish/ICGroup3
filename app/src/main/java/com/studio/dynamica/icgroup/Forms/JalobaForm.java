package com.studio.dynamica.icgroup.Forms;

public class JalobaForm {
   private String date;
   private String clients;
   private String name;
   private String position;
   private String text;
    public JalobaForm(){}
    public JalobaForm(String date, String clients, String name, String position, String text){
        this.date=date;
        this.clients=clients;
        this.name=name;
        this.position=position;
        this.text=text;
    }

    public String getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getClients() {
        return clients;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }
}
