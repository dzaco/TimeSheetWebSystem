package com.epstein.entity;

import javax.persistence.*;

@Entity @Table(name = "roles")
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id") private int id;
    @Column(name = "role") private String role;

    public Role() {    }

    public Role(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public Role(String authority) {
        this.setRole(authority);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role.toUpperCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role1 = (Role) o;
        return this.role != null ? role.equals(role1.role) : role1.role == null;
    }

}
