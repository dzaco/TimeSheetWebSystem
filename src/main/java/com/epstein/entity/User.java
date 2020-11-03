package com.epstein.entity;

import com.epstein.model.Roles;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "position")
    private String position;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    //private String role;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Column(name = "employment_time")
    private int employmentTime;
    @Column(name = "active")
    private boolean active;


    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getName() {
        return this.toString();
    }

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

//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }


    public Roles getRolesClass() {
        return new Roles(roles);
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }
    public void setRole(Role role) {
        if(this.roles == null )
            this.roles = new Roles( new HashSet<>() );

        if( !this.getRolesClass().hasRole(role) )
            this.roles.add( role );
    }
    public void setRole(String role) {
        this.setRole( new Role(role) );
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
