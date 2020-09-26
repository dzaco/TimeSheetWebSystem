package com.epstein.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login( ) {
        return "login";
    }

//    @PostMapping("/postlogin")
//    public String postLogin(@ModelAttribute String inputEmail, @ModelAttribute String inputPassword, Model model) {
//        System.out.println(inputEmail);
//        return "index";
//    }
}
