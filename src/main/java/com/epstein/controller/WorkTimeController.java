package com.epstein.controller;

import com.epstein.entity.WorkTime;
import com.epstein.factory.ModelFactory;
import com.epstein.model.DateInfo;
import com.epstein.service.RoleService;
import com.epstein.service.UserService;
import com.epstein.service.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller @RequestMapping("/worktimes")
public class WorkTimeController {
    @Autowired private WorkTimeService workTimeService;
    @Autowired private UserService userService;
    @Autowired private RoleService roleService;

    @Autowired private ModelFactory modelFactory;


    @GetMapping("/add")
    public String add(Model model) {
        model = modelFactory.setModel(model)
                .withDateTime()
                .create();

        model.addAttribute("worktime", new WorkTime() );
        model.addAttribute("page" , "worktime-add");
        return "base";
    }

    @PostMapping("/add")
    public RedirectView postAdd(@RequestParam("timeIn") String timeIn,
                                @RequestParam("timeOut") String timeOut,
                                Model model) {
        WorkTime workTime = new WorkTime();
        workTime.setDate(LocalDate.now());
        workTime.setUser(this.userService.getLogged());
        workTime.setTimeIn(LocalTime.parse(timeIn));
        if( !timeOut.equals("") )
            workTime.setTimeOut(LocalTime.parse(timeOut));
        else
            workTime.setTimeOut(LocalTime.parse("17:00"));
        this.workTimeService.save(workTime);

        return new RedirectView("/");
    }

}
