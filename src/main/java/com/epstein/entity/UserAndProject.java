//package com.epstein.entity;
//
//import javax.persistence.*;
//
//@Entity @Table(name = "users_projects")
//public class UserAndProject {
//    @Id @GeneratedValue(strategy = GenerationType.AUTO)
//    private int id;
//    @ManyToMany @JoinColumn(name = "user_id") User user;
//    @ManyToMany @JoinColumn(name = "project_id") Project project;
//
//    public UserAndProject() {    }
//
//    public UserAndProject(int id, User user, Project project) {
//        this.id = id;
//        this.user = user;
//        this.project = project;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Project getProject() {
//        return project;
//    }
//
//    public void setProject(Project project) {
//        this.project = project;
//    }
//}
