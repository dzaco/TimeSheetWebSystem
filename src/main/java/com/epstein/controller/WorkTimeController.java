package com.epstein.controller;

import com.epstein.entity.WorkTime;
import com.epstein.model.DateInfo;
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
public class WorkTimeController extends IController{
    @Autowired private WorkTimeService workTimeService;


    @GetMapping("/add")
    public String add(Model model) {
        this.mainAttribute(model);

        model.addAttribute("worktime", new WorkTime() );
        model.addAttribute("now", LocalDateTime.now() );

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


    @Override
    String getAll(Model model) {
        return null;
    }

    @Override
    String getById(int id, Model model) {
        return null;
    }

    @Override
    String edit(int id, Model model) {
        return null;
    }
}
