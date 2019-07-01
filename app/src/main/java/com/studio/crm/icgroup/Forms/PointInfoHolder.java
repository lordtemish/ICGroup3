package com.studio.crm.icgroup.Forms;

public class PointInfoHolder {
    String id, name, city;
    int location;
   public PointInfoHolder(){

   }

    public void setId(String id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLocation() {
        return location;
    }

    public String getCity() {
        return city;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
