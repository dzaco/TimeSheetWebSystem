package com.epstein.controller;

import com.epstein.service.ContractService;
import com.epstein.service.DepartmentService;
import com.epstein.service.RoleService;
import com.epstein.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;

@Controller
public class TestController {

    @Autowired
    UserService userService;
    @Autowired
    DepartmentService depService;
    @Autowired
    ContractService conService;
    @Autowired private RoleService roleService;



    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("logged", this.userService.getLogged());
        model.addAttribute("roleService", roleService);

//        model.addAttribute("month", Calendar.getInstance().getDisplayName( Calendar.MONTH, Calendar.LONG, Locale.getDefault() ));
       // model.addAttribute("month", Month.of( Calendar.getInstance().get(Calendar.MONTH) ).getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault()) );

        LocalDate now = LocalDate.now();
        LocalDate firstDay = now.minusDays( now.getDayOfMonth()-1 );
        LocalDate lastDay =  LocalDate.of(now.getYear(), now.getMonth().plus(1), 1).minusDays(1);

        model.addAttribute("date", now );
        model.addAttribute("firstDay", firstDay);
        model.addAttribute("lastDay", lastDay);
        model.addAttribute("offSet", firstDay.getDayOfWeek().getValue()-2);

        model.addAttribute("page" , "full-calendar");



        return "base";
    }

}