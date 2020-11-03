package com.epstein.controller;

import com.epstein.entity.Department;
import com.epstein.entity.Project;
import com.epstein.entity.Timesheet;
import com.epstein.entity.User;
import com.epstein.dto.UserDTO;
import com.epstein.factory.ModelFactory;
import com.epstein.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
@Controller @RequestMapping("users")
public class UserController {

    @Autowired private UserService userService;
    @Autowired private RoleService roleService;
    @Autowired private DepartmentService departmentService;
    @Autowired private ContractService contractService;
    @Autowired private TimesheetService timesheetService;
    @Autowired private ProjectService projectService;

    @Autowired private ModelFactory modelFactory;

    @GetMapping("/get")
    public String getAll(Model model) {
        model = modelFactory.setModel(model)
                .withAllUsers()
                .withAddButton(true)
                .create();

        model.addAttribute("page", "users");
        return "base";
    }

    @GetMapping("/get-ex")
    public String getEx(Model model) {

        model = modelFactory.setModel(model)
                .withInactiveUsers()
                .create();

        model.addAttribute("page", "users");
        return "base";
    }

    @GetMapping("/get/{id}")
    public String getById(@PathVariable int id, Model model) {

        model = modelFactory.setModel(model)
                .withUser(id)
                .withUserTimesheets(id)
                .create();

        model.addAttribute("page", "user-details");
        return "base";
    }

    @GetMapping("/get/{id}/edit")
    public String edit(@PathVariable int id, Model model) {

        model.addAttribute("userForm" , new UserDTO() );

        model = modelFactory.setModel(model)
                .withUser(id)
                .withAllDepartments()
                .withAllContracts()
                .create();

        model.addAttribute("page", "user-details-edit");
        return "base";
    }

    @PostMapping(value = "/get/{id}/edit")
    public RedirectView postEdit(@PathVariable int id, @ModelAttribute(value="userDTO") UserDTO userDTO, Model model) {
        this.userService.updateUser(userDTO);

        return new RedirectView("/users/get/" +id);
    }

    @GetMapping("/add")
    public String add(Model model) {
        //User user = this.userService.sampleUser();
        model.addAttribute("user", new User());
        model.addAttribute("userForm" , new UserDTO() );

        model = modelFactory.setModel(model)
                .withAllDepartments()
                .withAllContracts()
                .create();

        model.addAttribute("page", "user-details-add");
        return "base";
    }

    @PostMapping("/add")
    public RedirectView postAdd(@ModelAttribute UserDTO userDTO, Model model) {
        //this.userService.updateUser( userService.getUserFromForm(userDTO,true));
        //model = modelFactory.setModel(model).create();

        this.userService.saveUser(this.userService.getUserFromForm(userDTO,true));
        return new RedirectView("/users/get");
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteUser(@PathVariable int id, Model model) {
        this.userService.deactivateUser(id);
        int count = departmentService.getDepartmentsOfSupervisor(id).size()
                + projectService.getProjectsOfSupervisor(id).size();

        if(count == 0)
            return new RedirectView("/users/get");
        else{
            return new RedirectView("/users/getAllUserItems/"+id+"/edit");
        }
    }

    @GetMapping("/rehire/{id}")
    public RedirectView rehireUser(@PathVariable int id, Model model) {
        User user = this.userService.getUserById(id);
        user.setActive(true);
        this.userService.updateUser(user);
        return new RedirectView("/users/get/id");
    }

    @GetMapping("/getAllUserItems/{id}/edit")
    public String editAll(@PathVariable int id, Model model) {

        model.addAttribute("departments", departmentService.getDepartmentsOfSupervisor(id) );
        model.addAttribute("projects", projectService.getProjectsOfSupervisor(id) );
        model.addAttribute("exManagerId" , id );

        model = modelFactory.setModel(model)
                .withAllUsers()
                .create();

        model.addAttribute("page", "department-list-edit");
        return "base";
    }

    @PostMapping("/getAllUserItems/{userId}/edit")
    public RedirectView postEditAll(@PathVariable int userId,
                                    @RequestParam("departmentManagerId") String[] departmentManagerId,
                                    @RequestParam("projectManagerId") String[] projectManagerId,
                                    Model model) {

        List<Department> departments = this.departmentService.getDepartmentsOfSupervisor(userId);
        int i = 0;
        for (Department department : departments) {
            department.setSuperior( this.userService.getUserById(Integer.parseInt(departmentManagerId[i++])));
            this.departmentService.updateDepartment(department);
        }

        List<Project> projects = this.projectService.getProjectsOfSupervisor(userId);
        i = 0;
        for (Project project : projects) {
            project.setManager( this.userService.getUserById(Integer.parseInt(projectManagerId[i++])));
            this.projectService.update(project);
        }

        return new RedirectView("/departments/get");
    }


    @GetMapping(value = "/get/{id}/csv")
    public void getCSV(@PathVariable int id, HttpServletResponse response) throws IOException {
        String csvFileName = "timesheet-" + this.userService.getUserById(id).getLastName() + ".csv";

        List<Timesheet> timesheets = this.timesheetService.getUserTimesheets(id);

        CsvService csvService = new CsvService(csvFileName, response);
        csvService.createCSV(timesheets);

     }

}
