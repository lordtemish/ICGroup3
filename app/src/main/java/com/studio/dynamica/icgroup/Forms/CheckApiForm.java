package com.studio.dynamica.icgroup.Forms;

import android.util.Log;

public class CheckApiForm {
    String kind, name;
    int quantity=0, removed=0, total=100;
    boolean check=false;
    String id;
    public  boolean isChanged(){
        return removed>0;
    }
    public CheckApiForm(String id, String n, String k, int q){
        name=n;kind=k;quantity=q;
        this.id=id;
    }

    public int getTotal() {
        Log.d("CheckApiForm",quantity+" "+removed);
        if(quantity>=removed)
        return Integer.parseInt(""+(Math.round(Double.parseDouble((quantity-removed)+"")/Double.parseDouble(quantity+"")*100)));
        else{
            return 0;
        }
    }

    public String getId() {
        return id;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setRemoved(int removed) {
        this.removed = removed;
    }

    public String getKind() {
        return kind;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getRemoved() {
        return removed;
    }

    public boolean isCheck() {
        return check;
    }
}
