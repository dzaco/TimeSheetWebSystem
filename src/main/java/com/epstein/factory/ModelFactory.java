package com.epstein.factory;

import com.epstein.entity.User;
import com.epstein.model.DateInfo;
import com.epstein.model.LayoutInfo;
import com.epstein.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Klasa slużąca do ujednolicenia nazw obiektów zawartych w modelu oraz łatwiejszego ich zastosowania.
 * Wystarczy użyć interesujące nas metody zaczynające się od with.. a następnie metodą create dostać model który dostarczy nam niezbędnych informacji do pliku html.
 *
 */
@Service
public class ModelFactory {
    private Model model;
    @Autowired private UserService userService;
    @Autowired private RoleService roleService;
    @Autowired private DepartmentService departmentService;
    @Autowired private TimesheetService timesheetService;
    @Autowired private ProjectService projectService;
    @Autowired private MessageService messageService;
    @Autowired private ContractService contractService;

    public ModelFactory setModel(Model model) {
        this.model = model;
        return this;
    }


    public ModelFactory withAllUsers() {
        model.addAttribute("users", userService.getActiveUsers());
        return this;
    }
    public ModelFactory withInactiveUsers() {
        model.addAttribute("users", userService.getInactiveUsers() );
        return this;
    }
    public ModelFactory withUser(int userID) {
        model.addAttribute("user", userService.getUserById(userID) );
        return this;
    }


    public ModelFactory withAllDepartments() {
        model.addAttribute("departments", departmentService.getDepartments() );
        return this;
    }
    public ModelFactory withDepartment(int departmentID) {
        model.addAttribute("department", departmentService.getDepartmentById(departmentID) );
        return this;
    }
    public ModelFactory withUsersInDepartment(int departmentID) {
        model.addAttribute("users", departmentService.getUsersInDepartment(departmentID, 1));
        return this;
    }

    public ModelFactory withAllTimesheets() {
        model.addAttribute("timesheets", timesheetService.getTimesheets() );
        return this;
    }
    public ModelFactory withTimesheet(int timesheetID) {
        model.addAttribute("timesheet", timesheetService.getTimesheetById(timesheetID) );
        return this;
    }
    public ModelFactory withUserTimesheets(int userID) {
        model.addAttribute("timesheets", timesheetService.getUserTimesheets(userID));
        return this;
    }

    public ModelFactory withAllProjects() {
        model.addAttribute("projects", projectService.getActiveProjects() );
        return this;
    }
    public ModelFactory withProject(int projectID) {
        model.addAttribute("project", projectService.getProjectById(projectID) );
        return this;
    }
    public ModelFactory withUsersInProject(int projectID) {
        model.addAttribute("users" , userService.getUsersInProject(projectID));
        return this;
    }

    public ModelFactory withAllContracts() {
        model.addAttribute("contracts", contractService.getContracts() );
        return this;
    }
    public ModelFactory withUserMessages(int userID) {
        model.addAttribute("messages", messageService.getUserMessages(userID));
        return this;
    }


    public ModelFactory withTime() {
        model.addAttribute("now", LocalTime.now());
        return this;
    }
    public ModelFactory withDate() {
        model.addAttribute("now", new DateInfo() );
        return this;
    }
    public ModelFactory withDateTime() {
        model.addAttribute("now", LocalDateTime.now());
        return this;
    }


    public ModelFactory withAddButton( boolean flag ) {
        model.addAttribute("withAddButton", flag);
        return this;
    }

    public Model getModel() {
        return this.model;
    }
    public Model create() {
        this.model.addAttribute("logged", this.userService.getLogged() );
        this.model.addAttribute("roleService", roleService);
        this.model.addAttribute("withAddButton", false);
        return this.getModel();
    }

}
