package com.studio.dynamica.icgroup.Forms;

public class OrderForm {
    String id;
    String date;
    String number;
    String place;
    String priority;
    String status;
    int num1, num2;
    public OrderForm(String date,String number, String place, String priority, String status,int num1,int num2){
        this.date=date;
        this.number=number;
        this.place=place;
        this.priority=priority;
        this.status=status;
        this.num1=num1;
        this.num2=num2;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
            return id;
    }

    public String getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public int getNum1() {
        return num1;
    }

    public int getNum2() {
        return num2;
    }
    public double getD1() {
        return Double.parseDouble(num1+"");
    }

    public double getD2() {
        return Double.parseDouble(num2+"");
    }

    public String getNumber() {
        return number;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }
}
