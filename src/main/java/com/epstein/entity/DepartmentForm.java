package com.epstein.entity;

public class DepartmentForm {

    private int id;
    private String name;
    private int superiorId;

    public DepartmentForm() {    }

    public DepartmentForm(int id, String name, int superiorId) {
        this.id = id;
        this.name = name;
        this.superiorId = superiorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(int superiorId) {
        this.superiorId = superiorId;
    }
}
