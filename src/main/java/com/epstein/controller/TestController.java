package com.epstein.controller;

import com.epstein.entity.Department;
import com.epstein.entity.User;
import com.epstein.service.ContractService;
import com.epstein.service.DepartmentService;
import com.epstein.service.RoleService;
import com.epstein.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    UserService service;
    @Autowired
    DepartmentService depService;
    @Autowired
    ContractService conService;

    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("page" , "testContent2");
        return "testBase";
    }

}