package com.epstein.controller;

import com.epstein.entity.Department;
import com.epstein.entity.User;
import com.epstein.entity.UserForm;
import com.epstein.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
@Controller @RequestMapping("users")
public class UserController extends IController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private TimesheetService timesheetService;

    @GetMapping("get")
    public String getAll(Model model) {
        List<User> users = userService.getActiveUsers();
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
        model.addAttribute("userForm" , new UserForm() );
        this.mainAttribute(model);

        model.addAttribute("page", "user-details-edit");
        return "base";
    }

    @PostMapping(value = "/get/{id}/edit")
    public RedirectView postEdit(@PathVariable int id, @ModelAttribute(value="userForm") UserForm userForm, Model model) {
        this.userService.updateUser( userService.getUserFromForm(userForm) );
        this.mainAttribute(model);

        return new RedirectView("users/get/" +id);
    }

    @GetMapping("/add")
    public String add(Model model) {
        //User user = this.userService.sampleUser();
        model.addAttribute("user",new User());
        model.addAttribute("departments", departmentService.getDepartments());
        model.addAttribute("contracts", contractService.getContracts());
        model.addAttribute("roles", roleService.getRolesList() );
        model.addAttribute("userForm" , new UserForm() );
        this.mainAttribute(model);

        model.addAttribute("page", "user-details-add");
        return "base";
    }

    @PostMapping("/add")
    public RedirectView postAdd(@ModelAttribute UserForm userForm, Model model) {
        this.userService.updateUser( userService.getUserFromForm(userForm,true));
        this.mainAttribute(model);
        return new RedirectView("/users/get");
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteUser(@PathVariable int id, Model model) {
        this.userService.deactivateUser(id);
        Department department = departmentService.getDepartmentOfSupervisor(id);
        this.mainAttribute(model);

        if(department == null)
            return new RedirectView("/users/get");
        else
            return new RedirectView("/departments/get/" + department.getId() + "/edit");
    }

}
