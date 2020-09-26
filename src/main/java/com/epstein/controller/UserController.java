package com.epstein.controller;

import com.epstein.entity.Department;
import com.epstein.entity.User;
import com.epstein.entity.UserForm;
import com.epstein.service.ContractService;
import com.epstein.service.DepartmentService;
import com.epstein.service.RoleService;
import com.epstein.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
@Controller @RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ContractService contractService;

    @GetMapping("get")
    public String getUsers(Model model) {
        List<User> users = userService.getActiveUsers();
        model.addAttribute("users", users);
        model.addAttribute("page", "users");
        model.addAttribute("logged", this.userService.getLogged());

        return "base";
    }

    @GetMapping("/get/{id}")
    public String getUserById(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("page", "user-details");
        model.addAttribute("logged", this.userService.getLogged());

        return "base";
    }

    @GetMapping("/get/{id}/edit")
    public String getEditUserFormById(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("departments", departmentService.getDepartments());
        model.addAttribute("contracts", contractService.getContracts());
        model.addAttribute("roles", RoleService.getRoles() );
        model.addAttribute("userForm" , new UserForm() );
        model.addAttribute("logged", this.userService.getLogged());

        model.addAttribute("page", "user-details-edit");
        return "base";
    }

    @PostMapping(value = "/get/{id}/edit")
    public String handleEditUserForm(@PathVariable int id, @ModelAttribute(value="userForm") UserForm userForm, Model model) {
        this.userService.updateUser( userService.getUserFromForm(userForm) );
        model.addAttribute("logged", this.userService.getLogged());

        return this.getUsers(model);
    }

    @GetMapping("/add")
    public String addUser(Model model) {
        //User user = this.userService.sampleUser();
        model.addAttribute("user",new User());
        model.addAttribute("departments", departmentService.getDepartments());
        model.addAttribute("contracts", contractService.getContracts());
        model.addAttribute("roles", RoleService.getRoles() );
        model.addAttribute("userForm" , new UserForm() );
        model.addAttribute("logged", this.userService.getLogged());

        model.addAttribute("page", "user-details-add");
        return "base";
    }

    @PostMapping("/add")
    public RedirectView handleAddUserForm(@ModelAttribute UserForm userForm, Model model) {
        this.userService.updateUser( userService.getUserFromForm(userForm,true));
        model.addAttribute("logged", this.userService.getLogged());
        return new RedirectView("/users/get");
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteUser(@PathVariable int id, Model model) {
        this.userService.deactivateUser(id);
        Department department = departmentService.getDepartmentOfSupervisor(id);
        model.addAttribute("logged", this.userService.getLogged());

        if(department == null)
            return new RedirectView("/users/get");
        else
            return new RedirectView("/departments/get/" + department.getId() + "/edit");
    }

}
