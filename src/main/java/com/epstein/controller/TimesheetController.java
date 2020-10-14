package com.epstein.controller;

import com.epstein.entity.Department;
import com.epstein.entity.Timesheet;
import com.epstein.model.DateInfo;
import com.epstein.service.ProjectService;
import com.epstein.service.RoleService;
import com.epstein.service.TimesheetService;
import com.epstein.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Controller @RequestMapping("/timesheets")
public class TimesheetController extends IController {

    @Autowired private TimesheetService timesheetService;
    @Autowired private ProjectService projectService;


    @GetMapping("/get")
    public String getAll(Model model) {
        model.addAttribute("timesheets", timesheetService.getTimesheets());
        this.mainAttribute(model);

        model.addAttribute("page", "timesheets");
        return "base";
    }

    @GetMapping("/get/{id}")
    public String getById(@PathVariable int id, Model model) {
        Timesheet timesheet = this.timesheetService.getTimesheetById(id);
        model.addAttribute("timesheet", timesheet);
        this.mainAttribute(model);

        model.addAttribute("page", "timesheet-details");
        return "base";
    }

    @GetMapping("/get/{id}/edit")
    public String edit(int id, Model model) {
        model.addAttribute("timesheet", this.timesheetService.getTimesheetById(id));
        this.mainAttribute(model);

        model.addAttribute("page", "department-details-edit");
        return "base";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("timesheet", new Timesheet());
        model.addAttribute("projects", projectService.getActiveProjects());
        model.addAttribute("date", new DateInfo() );

        this.mainAttribute(model);

        model.addAttribute("page", "timesheet-details-add");
        return "base";
    }

    @PostMapping("/add")
    public String postAdd(@RequestParam("projectId") int projectId,
                          @RequestParam("stage") int stage,
                          @RequestParam("month") String month,
                          @RequestParam(value = "date") String[] date,
                          Model model) {

        Timesheet timesheet = new Timesheet();
        timesheet.setProject( this.projectService.getProjectById(projectId));
        timesheet.setStage(stage);

        timesheet.setMonthAndYear( month );
        timesheet.setUser( this.userService.getLogged() );

        int[] hoursInDays = new int[31];
        for(int i = 0; i < 31; i++ ) {
            if(date[i].equals("") || date[i].equals(" "))
                hoursInDays[i] = 0;
            else
                hoursInDays[i] = Integer.parseInt( date[i] );
        }
        timesheet.setHours(hoursInDays);

        if(!timesheetService.exist(timesheet)) {
            this.timesheetService.save(timesheet);
            return this.getAll(model);
            //return new RedirectView("/timesheets/get");
        }
        else {
            System.out.println("timesheet juz istnieje");
            model.addAttribute("dangerMsg", "Timesheet dla podanego miesiąca i projektu już istnieje");
            return this.add(model);
        }

    }
}
