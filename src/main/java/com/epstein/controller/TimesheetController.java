package com.epstein.controller;

import com.epstein.entity.Timesheet;
import com.epstein.factory.ModelFactory;
import com.epstein.service.ProjectService;
import com.epstein.service.RoleService;
import com.epstein.service.TimesheetService;
import com.epstein.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller @RequestMapping("/timesheets")
public class TimesheetController {

    @Autowired private TimesheetService timesheetService;
    @Autowired private ProjectService projectService;
    @Autowired private UserService userService;
    @Autowired private RoleService roleService;

    @Autowired private ModelFactory modelFactory;


    @GetMapping("/get")
    public String getAll(Model model) {

        model = modelFactory.setModel(model)
                .withAllTimesheets()
                .withAddButton(false)
                .create();

        model.addAttribute("page", "timesheets");

        return "base";
    }

    @GetMapping("/get/{id}")
    public String getById(@PathVariable int id, Model model) {

        model = modelFactory.setModel(model)
                .withTimesheet(id)
                .withDate()
                .create();

        model.addAttribute("page", "timesheet-details");
        return "base";
    }

    @GetMapping("/get/{id}/edit")
    public String edit(@PathVariable int id, Model model) {

        model = modelFactory.setModel(model)
                .withTimesheet(id)
                .withAllProjects()
                .withDate()
                .create();

        model.addAttribute("page", "timesheet-details-edit");
        return "base";
    }

    @PostMapping("/get/{id}/edit")
    public RedirectView postEdit(@PathVariable int id,
                                 @RequestParam("projectId") int projectId,
                                 @RequestParam("stage") int stage,
                                 @RequestParam("month") String month,
                                 @RequestParam(value = "date") Integer[] date,
                                 Model model) {

        Timesheet timesheet = this.timesheetService.getTimesheetById(id);
        timesheet.setHours(date);
        timesheetService.save( timesheet );

        return new RedirectView("/timesheets/get/" + timesheet.getId() );
    }


        @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("timesheet", new Timesheet());

        model = modelFactory.setModel(model)
                .withAllProjects()
                .withDate()
                .create();

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

        if(timesheetService.exist(timesheet)) {
            model.addAttribute("dangerMsg", "Timesheet już istnieje. Przechodze do edytowania.");
            Optional<Timesheet> toEdit = timesheetService.getTimesheet(timesheet);
            if(toEdit.isPresent())
                return this.edit(toEdit.get().getId(), model);
        }

        List<Integer> hoursInDays = new ArrayList<>();
        for(String h : date ) {
            hoursInDays.add( h.isBlank() ? 0 : Integer.parseInt( h ));
        }
        while(hoursInDays.size() < 31) {
            hoursInDays.add(0);
        }


        Integer[] intArray = new Integer[hoursInDays.size()];
        intArray = hoursInDays.toArray(intArray);

        timesheet.setHours(intArray);

        if(!timesheetService.exist(timesheet)) {
            this.timesheetService.save(timesheet);
            return this.getById(timesheet.getId(), model);
            //return new RedirectView("/timesheets/get");
        }
        else {
            System.out.println("timesheet juz istnieje");
            model.addAttribute("dangerMsg", "Timesheet dla podanego miesiąca i projektu już istnieje");
            return this.add(model);
        }

    }
}
