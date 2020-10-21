package com.epstein.controller;

import com.epstein.configuration.ModelConfig;
import com.epstein.entity.Project;
import com.epstein.service.ProjectService;
import com.epstein.service.RoleService;
import com.epstein.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller @RequestMapping("/projects")
public class ProjectController extends IController {

    @Autowired private ProjectService projectService;

    @GetMapping("/get")
    public String getAll(Model model) {
        model.addAttribute("projects", this.projectService.getActiveProjects());
        model.addAttribute("page" , "projects");
        model.addAttribute("model", new ModelConfig() );
        this.mainAttribute(model);

        return "base";
    }


    @GetMapping("/get/{id}")
    public String getById(@PathVariable int id, Model model) {
        Project project = this.projectService.getProjectById(id);
        model.addAttribute("project", project);
        model.addAttribute("users", this.userService.getUserInProject(project.getId()));
        model.addAttribute("userHeader", "Lista Pracowników w Projekcie");
        model.addAttribute("model", new ModelConfig().withAddButton(false) );
        this.mainAttribute(model);

        model.addAttribute("page", "project-details");
        return "base";
    }

    @GetMapping("/get/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        Project project = this.projectService.getProjectById(id);
        model.addAttribute("project", project);
        model.addAttribute("users", this.userService.getUserInProject(project.getId()));
        model.addAttribute("allUsers", this.userService.getUsers());

        this.mainAttribute(model);

        //TODO mozliwosc dodania i usuwania pracownikow z projektu
        model.addAttribute("page", "project-details-edit");
        return "base";
    }

    @PostMapping("/get/{id}/edit")
    public RedirectView postEdit(@PathVariable int id, @ModelAttribute(value="project") Project project, Model model) {
        System.out.println(project.getEndDate());
        this.projectService.update(project);
        return new RedirectView("/projects/get/" +id);
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("project" , new Project());
        this.mainAttribute(model);

        model.addAttribute("page", "project-details-add");
        return "base";
    }

    @PostMapping("/add")
    public RedirectView postAdd(@ModelAttribute Project postProject, Model model) {
        this.mainAttribute(model);

        this.projectService.update( postProject );
        return new RedirectView("/projects/get");
    }

}
