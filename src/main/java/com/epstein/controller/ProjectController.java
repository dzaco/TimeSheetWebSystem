package com.epstein.controller;

import com.epstein.configuration.ModelConfig;
import com.epstein.entity.Project;
import com.epstein.entity.Timesheet;
import com.epstein.factory.ModelFactory;
import com.epstein.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller @RequestMapping("/projects")
public class ProjectController {

    @Autowired private ProjectService projectService;
    @Autowired private UserService userService;
    @Autowired private RoleService roleService;
    @Autowired private TimesheetService timesheetService;

    @Autowired private ModelFactory modelFactory;

    @GetMapping("/get")
    public String getAll(Model model) {

        model = modelFactory.setModel(model)
                .withAllProjects()
                .create();

        model.addAttribute("page" , "projects");
        return "base";
    }


    @GetMapping("/get/{id}")
    public String getById(@PathVariable int id, Model model) {
        Project project = this.projectService.getProjectById(id);

        model = modelFactory.setModel(model)
                .withProject(id)
                .withUsersInProject(id)
                .withTimesheetsInProject(id)
                .create();

        model.addAttribute("page", "project-details");
        return "base";
    }

    @GetMapping("/get/{id}/edit")
    public String edit(@PathVariable int id, Model model) {

        model.addAttribute("allUsers", this.userService.getUsers());
        model = modelFactory.setModel(model)
                .withProject(id)
                .withUsersInProject(id)
                .create();

        //TODO mozliwosc dodania i usuwania pracownikow z projektu
        model.addAttribute("page", "project-details-edit");
        return "base";
    }

    @PostMapping("/get/{id}/edit")
    public RedirectView postEdit(@PathVariable int id, @ModelAttribute(value="project") Project project, Model model) {

        this.projectService.update(project);
        return new RedirectView("/projects/get/" +id);
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("project" , new Project());
        model = modelFactory.setModel(model).create();

        model.addAttribute("page", "project-details-add");
        return "base";
    }

    @PostMapping("/add")
    public RedirectView postAdd(@ModelAttribute Project postProject, Model model) {
        model = modelFactory.setModel(model).create();

        this.projectService.update( postProject );
        return new RedirectView("/projects/get");
    }

    @GetMapping(value = "/get/{id}/csv")
    public void getCSV(@PathVariable int id, HttpServletResponse response) throws IOException {
        String csvFileName = "timesheet-" + this.projectService.getProjectById(id).getName() + ".csv";

        List<Timesheet> timesheets = this.timesheetService.getTimesheetsInProject(id);
        Map<Timesheet, Integer> map = new HashMap<>();

        Comparator<Timesheet> comparator = Comparator.comparing((Timesheet t) -> t.getProject().getName() )
                .thenComparing(Timesheet::getStage)
                .thenComparing(Timesheet::getMonthValue)
                .thenComparing(Timesheet::getYear);

        for(Timesheet timesheet : timesheets) {
            boolean found = false;
            Timesheet foundTimesheet = null;

            for(Timesheet uniqueTimesheet : map.keySet()) {
                if( comparator.compare(timesheet,uniqueTimesheet) == 0 ) {
                    found = true;
                    foundTimesheet = uniqueTimesheet;
                    break;
                }
            }
            if(found) {
                map.replace(foundTimesheet,foundTimesheet.getSum() + timesheet.getSum());
            }
            else map.put(timesheet, timesheet.getSum());

            if(map.isEmpty()) map.put(timesheet, timesheet.getSum());
        }


        CsvService csvService = new CsvService(csvFileName, response);
        csvService.createCSV(map);

    }

}
