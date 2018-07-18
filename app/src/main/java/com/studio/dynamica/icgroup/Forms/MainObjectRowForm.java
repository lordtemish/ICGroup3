package com.studio.dynamica.icgroup.Forms;

public class MainObjectRowForm {
    String id;
    String name;
    int percentage;
    int people;
    int falses;
    int categories;
    public MainObjectRowForm(){

    }
    public MainObjectRowForm(String name,int percentage, int people, int falses, int categories){
        this.categories=categories;this.name=name;this.people=people;this.percentage=percentage;this.falses=falses;
    }
    public MainObjectRowForm(String id, String name,int percentage, int people, int falses, int categories){
        this.categories=categories;this.name=name;this.people=people;this.percentage=percentage;this.falses=falses;this.id=id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCategories() {
        return categories;
    }

    public int getFalses() {
        return falses;
    }

    public int getPeople() {
        return people;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategories(int categories) {
        this.categories = categories;
    }

    public void setFalses(int falses) {
        this.falses = falses;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
