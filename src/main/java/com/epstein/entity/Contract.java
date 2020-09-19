package com.epstein.entity;

import javax.persistence.*;

@Entity
@Table(name = "contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "contract_id") private int contract_id;
    @Column(name = "name") private String name;

    public Contract() {    }

    public Contract(String name) {
        this.name = name;
    }

    public int getId() {
        return contract_id;
    }

    public void setId(int id) {
        this.contract_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
