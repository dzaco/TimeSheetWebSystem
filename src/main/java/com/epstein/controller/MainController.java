package com.epstein.controller;

import com.epstein.factory.ModelFactory;
import com.epstein.service.MessageService;
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
    @Autowired private MessageService messageService;

    @Autowired private ModelFactory modelFactory;

    @GetMapping("/")
    public String index(Model model) {

        model = modelFactory.setModel(model)
                .withUserMessages( this.userService.getLogged().getId() )
                .create();

        System.out.println(userService.getLogged().getRolesClass() );
        System.out.println(roleService.getHighRoles() );
        System.out.println(userService.getLogged().getRolesClass().hasAnyRole( roleService.getHighRoles() ));




        return "index";
    }

}
