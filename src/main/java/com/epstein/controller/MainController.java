package com.epstein.controller;

import com.epstein.entity.Message;
import com.epstein.factory.ModelFactory;
import com.epstein.service.Checker;
import com.epstein.service.MessageService;
import com.epstein.service.RoleService;
import com.epstein.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired private UserService userService;
    @Autowired private RoleService roleService;
    @Autowired private MessageService messageService;

    @Autowired private ModelFactory modelFactory;

    @Autowired private Checker checker;

    @GetMapping("/")
    public String index(Model model) {

        System.out.println(
                userService.getLogged()
                        .getRolesClass()
                        .hasAnyRole(
                                roleService.getHighRoles()
                        ));

        List<Message> messages = checker.checkForMessage();

        model = modelFactory.setModel(model)
//                .withUserMessages( this.userService.getLogged().getId() )
                .withMessages( messages )
                .create();

        return "index";
    }

}
