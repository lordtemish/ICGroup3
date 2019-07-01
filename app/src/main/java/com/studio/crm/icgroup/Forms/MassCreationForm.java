package com.studio.crm.icgroup.Forms;

public class MassCreationForm {
    String name, vendor, unit, id;
    int total=0, n1=0,n2=0;
    boolean set=false;

    public MassCreationForm(String n, String v, String u, int t, int n1, int n2){
        name=n;vendor=v;unit=u;total=t;this.n1=n1;this.n2=n2;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isSet() {
        return set;
    }

    public String getVendor() {
        return vendor;
    }

    public String getUnit() {
        return unit;
    }

    public int getTotal() {
        return total;
    }

    public String getName() {
        return name;
    }

    public int getN2() {
        return n2;
    }

    public int getN1() {
        return n1;
    }
    public int getMax(){
        if(n2>n1){
            return n2-n1;
        }
        else return 0;
    }
    public void setSet(boolean set) {
        this.set = set;
    }
}
