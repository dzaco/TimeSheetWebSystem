package com.epstein.controller;

import com.epstein.service.RoleService;
import com.epstein.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired private UserService userService;
    @Autowired private RoleService roleService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("logged", this.userService.getLogged());
        model.addAttribute("roleService", roleService);
        return "index";
    }


}
