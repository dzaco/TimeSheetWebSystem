package com.epstein.controller;

import com.epstein.entity.Department;
import com.epstein.entity.Project;
import com.epstein.entity.User;
import com.epstein.model.UserDTO;
import com.epstein.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
@Controller @RequestMapping("users")
public class UserController extends IController {

    @Autowired private DepartmentService departmentService;
    @Autowired private ContractService contractService;
    @Autowired private TimesheetService timesheetService;
    @Autowired private ProjectService projectService;

    @GetMapping("get")
    public String getAll(Model model) {
        List<User> users = userService.getActiveUsers();
        model.addAttribute("users", users);
        model.addAttribute("page", "users");
        this.mainAttribute(model);

        return "base";
    }
    @GetMapping("get-ex")
    public String getEx(Model model) {
        List<User> users = userService.getInactiveUsers();
        model.addAttribute("users", users);
        model.addAttribute("page", "users");
        this.mainAttribute(model);

        return "base";
    }

    @GetMapping("/get/{id}")
    public String getById(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("page", "user-details");
        this.mainAttribute(model);

        model.addAttribute("timesheets", timesheetService.getUserTimesheets(id));

        return "base";
    }

    @GetMapping("/get/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("departments", departmentService.getDepartments());
        model.addAttribute("contracts", contractService.getContracts());
        model.addAttribute("roles", roleService.getRolesList() );
        model.addAttribute("userForm" , new UserDTO() );
        this.mainAttribute(model);

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
        model.addAttribute("user",new User());
        model.addAttribute("departments", departmentService.getDepartments());
        model.addAttribute("contracts", contractService.getContracts());
        model.addAttribute("roles", roleService.getRolesList() );
        model.addAttribute("userForm" , new UserDTO() );
        this.mainAttribute(model);

        model.addAttribute("page", "user-details-add");
        return "base";
    }

    @PostMapping("/add")
    public RedirectView postAdd(@ModelAttribute UserDTO userDTO, Model model) {
        this.userService.updateUser( userService.getUserFromForm(userDTO,true));
        this.mainAttribute(model);
        return new RedirectView("/users/get");
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteUser(@PathVariable int id, Model model) {
        this.userService.deactivateUser(id);
        int count = departmentService.getDepartmentsOfSupervisor(id).size()
                + projectService.getProjectsOfSupervisor(id).size();

        this.mainAttribute(model);

        if(count == 0)
            return new RedirectView("/users/get");
        else{
            return new RedirectView("/users/getAllUserItems/"+id+"/edit");
        }
    }

    @GetMapping("/getAllUserItems/{id}/edit")
    public String editAll(@PathVariable int id, Model model) {

        model.addAttribute("departments", departmentService.getDepartmentsOfSupervisor(id) );
        model.addAttribute("projects", projectService.getProjectsOfSupervisor(id) );

        model.addAttribute("users", userService.getActiveUsers() );
        model.addAttribute("exManagerId" , id );
        this.mainAttribute(model);

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

}
