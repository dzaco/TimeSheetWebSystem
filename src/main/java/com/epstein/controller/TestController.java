package com.epstein.controller;

import com.epstein.entity.MessageSchema;
import com.epstein.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class TestController {

    @Autowired
    UserService userService;
    @Autowired
    DepartmentService depService;
    @Autowired
    ContractService conService;
    @Autowired private RoleService roleService;
    @Autowired private MessageService messageService;


    @GetMapping("/test")
    public String test(Model model) {

        return "base";
    }

    @GetMapping("/testGet")
    public String testGet(Model model) {
        model.addAttribute("logged", this.userService.getLogged());
        model.addAttribute("roleService", roleService);

        model.addAttribute("messages", this.messageService.getAll());
        model.addAttribute("page", "messages");

        return "base";
    }

}