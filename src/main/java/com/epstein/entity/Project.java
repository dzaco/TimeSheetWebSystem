package com.epstein.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity @Table(name = "projects")
public class Project {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int project_id;
    @Column(name = "code") private String code;
    @Column(name = "name") private String name;
    @OneToOne @JoinColumn(name = "manager_id") private User manager;
    @Column(name = "start_date") private Date startDate;
    @Column(name = "end_date") private Date endDate;
    @Column(name = "other") private String other;

    public Project() {    }

    public Project(int id, String code, String name, User manager, Date startDate, Date endDate, String other) {
        this.project_id = id;
        this.code = code;
        this.name = name;
        this.manager = manager;
        this.startDate = startDate;
        this.endDate = endDate;
        this.other = other;
    }

    public int getId() {
        return project_id;
    }

    public void setId(int id) {
        this.project_id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }


    @Override
    public String toString() {
        return this.getName();
    }
}
