package com.epstein.controller;

import com.epstein.service.RoleService;
import com.epstein.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

public abstract class IController {

    @Autowired protected UserService userService;
    @Autowired protected RoleService roleService;

    public void mainAttribute(Model model) {
        model.addAttribute("logged", this.userService.getLogged());
        model.addAttribute("roleService", roleService);
    }

    abstract String getAll(Model model);
    abstract String getById(@PathVariable int id, Model model);
    abstract String edit(@PathVariable int id, Model model);
    //RedirectView postEdit(@PathVariable int id, @ModelAttribute(value="departmentForm") DepartmentForm departmentForm, Model model);
    abstract String add(Model model);
    //RedirectView postAdd(@ModelAttribute DepartmentForm departmentForm, Model model);
}
