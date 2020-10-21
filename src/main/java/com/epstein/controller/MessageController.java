package com.epstein.controller;

import com.epstein.entity.Message;
import com.epstein.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/messages")
public class MessageController extends IController {

    @Autowired private MessageService messageService;

    @GetMapping("/")
    public String getAll(Model model) {
        this.mainAttribute(model);
        model.addAttribute("messages", this.messageService.getUserMessages( this.userService.getLogged() ) );

        model.addAttribute("page" , "messages");
        return "base";
    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable int id, Model model) {
        this.messageService.setStatus(0, id);

        return new RedirectView("/");
    }

    @Override
    String getById(int id, Model model) {
        return null;
    }

    @Override
    String edit(int id, Model model) {
        return null;
    }

    @Override
    String add(Model model) {
        return null;
    }

}
