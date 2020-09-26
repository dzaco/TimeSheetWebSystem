package com.epstein.entity;

import javax.persistence.*;
import java.util.ArrayList;

@Entity @Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id") private int user_id;
    @Column(name = "firstname") private String firstName;
    @Column(name = "lastname") private String lastName;
    @Column(name = "email") private String email;
    @Column(name = "password") private String password;

    @ManyToOne @JoinColumn(name="department_id")
    private Department department;

    @Column(name = "position") private String position;

    @ManyToOne @JoinColumn(name="contract_id")
    private Contract contract;

    @Column(name = "role") private String role;
    @Column(name = "employment_time") private int employmentTime;
    @Column(name = "active") private boolean active;



    public User() {
//        this.firstName = "firstName";
//        this.lastName = "lastName";
//        this.email = "email";
//        this.password = "password";
//        this.department = new Department("Dział",this);
//        this.position = "position";
//        this.contract = new Contract("Umowa");
//        this.role = "USER";
//        this.employmentTime = 0;
//        this.active = true;
    }

    public User(String firstName, String lastName, String email, String password, Department department, String position, Contract contract, String role, int employmentTime, boolean active) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.department = department;
        this.position = position;
        this.contract = contract;
        this.role = role;
        this.employmentTime = employmentTime;
        this.active = active;
    }

    public int getId() {
        return user_id;
    }

    public void setId(int id) {
        this.user_id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() { return this.toString(); }

    public Department getDepartment() {
        return department;
    }
    public String getDepartmentName() {
        return department.getName();
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Contract getContract() {
        return contract;
    }
    public String getContractName() {
        return contract.getName();
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getEmploymentTime() {
        return employmentTime;
    }

    public void setEmploymentTime(int employmentTime) {
        this.employmentTime = employmentTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    // extra to html needed
    public String getInitials() {
        char first = this.getFirstName().charAt(0);
        char last = this.getLastName().charAt(0);
        return String.valueOf(first) + String.valueOf(last);
    }

    @Override
    public String toString() {
        return this.getFirstName() + " " + this.getLastName();
    }
}
