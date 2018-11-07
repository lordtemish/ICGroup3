package com.studio.dynamica.icgroup.Forms;

public class MainEmployeeForm {
    String id;
    String name; int rate, result_rate ;
    String url="", position;

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public MainEmployeeForm(){}

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setResult_rate(int result_rate) {
        this.result_rate = result_rate;
    }

    public String getName() {
        return name;
    }

    public int getRate() {
        return rate;
    }

    public int getResult_rate() {
        return result_rate;
    }
}
