package com.epstein.controller;

import com.epstein.entity.Project;
import com.epstein.service.ProjectService;
import com.epstein.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller @RequestMapping("/projects")
public class ProjectController {

    @Autowired private ProjectService projectService;
    @Autowired private UserService userService;

    @GetMapping("/get")
    public String getProjects(Model model) {
        model.addAttribute("projects", this.projectService.getActiveProjects());
        model.addAttribute("page" , "projects");
        return "base";
    }


    @GetMapping("/get/{id}")
    public String getProjectById(@PathVariable int id, Model model) {
        Project project = this.projectService.getProjectById(id);
        model.addAttribute("project", project);
        model.addAttribute("users", this.userService.getUserInProject(project.getId()));
        model.addAttribute("userHeader", "Lista Pracowników w Projekcie");
        model.addAttribute("page", "project-details");
        return "base";
    }

    @GetMapping("/get/{id}/edit")
    public String getEditProjectById(@PathVariable int id, Model model) {
        Project project = this.projectService.getProjectById(id);
        model.addAttribute("project", project);
        model.addAttribute("users", this.userService.getUserInProject(project.getId()));

        //TODO mozliwosc dodania i usuwania pracownikow z projektu
        model.addAttribute("page", "project-details-edit");
        return "base";
    }

    @PostMapping("/get/{id}/edit")
    public String postEditProject(@PathVariable int id, @ModelAttribute(value="postProject") Project postProject, Model model) {
        this.projectService.update(postProject);
        return this.getProjectById(id,model);
    }

    @GetMapping("/add")
    public String addProject(Model model) {
        model.addAttribute("project" , new Project());
        model.addAttribute("page", "project-details-add");
        return "base";
    }

    @PostMapping("/add")
    public RedirectView postAddingProject(@ModelAttribute Project postProject, Model model) {
        this.projectService.update( postProject );
        return new RedirectView("/projects/get");
    }

}
