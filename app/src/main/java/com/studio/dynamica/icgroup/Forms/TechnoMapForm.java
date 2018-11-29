package com.studio.dynamica.icgroup.Forms;

public class TechnoMapForm {
    String id;
    String time;
    String place;
    String category;
    String status;
    int stat=0;
    String method;
    String period;
    boolean result=false;

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public TechnoMapForm(){

    }
    public TechnoMapForm(String t, String pl, String s, String m, String pe){
        time=t;
        place=pl;
        status=s;
        method=m;
        period=pe;
    }
    public TechnoMapForm(String t, String pl, String m, String pe, int stat){
        time=t;
        place=pl;
        method=m;
        period=pe;
        this.stat=stat;
        switch (stat){
            case 0:
                status="Провалено";
                break;
            case 1:
                status="Выполнено";
                break;
            case 2:
                status="В процессе";
                break;
            case 3:
                status="Актуально";
                break;
            case 4:
                status="На просрочке";
                break;
        }
    }
    public void setStat(int stat) {
        this.stat = stat;
        switch (stat){
            case 0:
                status="Провалено";
                break;
            case 1:
                status="Выполнено";
                break;
            case 2:
                status="В процессе";
                break;
            case 3:
                status="Актуально";
                break;
            case 4:
                status="На просрочке";
                break;
        }
    }

    public int getStat() {
        return stat;
    }

    public String getStatus() {
        return status;
    }

    public String getCategory() {
        return category;
    }

    public String getMethod() {
        return method;
    }

    public String getPeriod() {
        return period;
    }

    public String getPlace() {
        return place;
    }

    public String getTime() {
        return time;
    }
}
