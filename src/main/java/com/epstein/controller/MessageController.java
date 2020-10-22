package com.epstein.controller;

import com.epstein.entity.Message;
import com.epstein.factory.ModelFactory;
import com.epstein.service.MessageService;
import com.epstein.service.RoleService;
import com.epstein.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/messages")
public class MessageController {

    @Autowired private MessageService messageService;
    @Autowired private UserService userService;
    @Autowired private RoleService roleService;

    @Autowired private ModelFactory modelFactory;

    @GetMapping("/")
    public String getAll(Model model) {

        model = modelFactory.setModel(model)
                .withUserMessages(userService.getLogged().getId())
                .create();

        model.addAttribute("page" , "messages");
        return "base";
    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable int id, Model model) {
        this.messageService.setStatus(0, id);

        return new RedirectView("/");
    }



}
