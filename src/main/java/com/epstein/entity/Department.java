package com.epstein.entity;

import javax.persistence.*;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "department_id") private int department_id;
    @Column(name = "name") private String name;
    @OneToOne @JoinColumn(name = "superior_id")
    private User superior;


    public Department() {    }

    public Department(String name, User superior) {
        this.name = name;
        this.superior = superior;
    }

    public int getId() {
        return department_id;
    }

    public void setId(int id) {
        this.department_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getSuperior() {
        return superior;
    }

    public void setSuperior(User superior) {
        this.superior = superior;
    }

    public int getSuperiorId() {
        return this.getSuperior().getId();
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
