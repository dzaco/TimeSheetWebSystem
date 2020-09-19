//package com.epstein.controller;
//
//import com.epstein.entity.User;
//import com.epstein.entity.UserForm;
//import com.epstein.service.ContractService;
//import com.epstein.service.DepartmentService;
//import com.epstein.service.RoleService;
//import com.epstein.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.view.RedirectView;
//
//import javax.management.modelmbean.ModelMBeanOperationInfo;
//import java.util.List;
//
//@Controller
//@RequestMapping("admin")
//public class AdminController {
//
//    @Autowired
//    UserService userService;
//    @Autowired
//    DepartmentService departmentService;
//    @Autowired
//    ContractService contractService;
//
//    @GetMapping("users/get")
//    public String getUsers(Model model) {
//        List<User> users = userService.getActiveUsers();
//        model.addAttribute("users", users);
//        model.addAttribute("page", "users");
//        return "base";
//    }
//
//    @GetMapping("users/get/{id}")
//    public String getUserById(@PathVariable int id, Model model) {
//        User user = userService.getUserById(id);
//        model.addAttribute("user", user);
//        model.addAttribute("page", "user-details");
//        return "base";
//    }
//
//    @GetMapping("users/get/{id}/edit")
//    public String getEditUserFormById(@PathVariable int id, Model model) {
//        User user = userService.getUserById(id);
//        model.addAttribute("user", user);
//        model.addAttribute("departments", departmentService.getDepartments());
//        model.addAttribute("contracts", contractService.getContracts());
//        model.addAttribute("roles", RoleService.getRoles() );
//        model.addAttribute("userForm" , new UserForm() );
//
//        model.addAttribute("page", "user-details-edit");
//        return "base";
//    }
//
//    @PostMapping(value = "users/get/{id}/edit")
//    public String handleEditUserForm(@PathVariable int id, @ModelAttribute(value="userForm") UserForm userForm, Model model) {
//        this.userService.updateUser( userService.getUserFromForm(userForm) );
//        return this.getUsers(model);
//    }
//
//    @GetMapping("users/add")
//    public String addUser(Model model) {
//        User user = this.userService.sampleUser();
//        model.addAttribute("user",user);
//        model.addAttribute("departments", departmentService.getDepartments());
//        model.addAttribute("contracts", contractService.getContracts());
//        model.addAttribute("roles", RoleService.getRoles() );
//        model.addAttribute("userForm" , new UserForm() );
//
//        model.addAttribute("page", "user-details-add");
//        return "base";
//    }
//
//    @PostMapping("users/add")
//    public RedirectView handleAddUserForm(@ModelAttribute UserForm userForm, Model model) {
//        this.userService.updateUser( userService.getUserFromForm(userForm));
//        return new RedirectView("/admin/users/get");
//    }
//
//    @GetMapping("users/delete/{id}")
//    public RedirectView deleteUser(@PathVariable int id, Model model) {
//        this.userService.deactivateUser(id);
//        return new RedirectView("/admin/users/get");
//    }
//
//}
