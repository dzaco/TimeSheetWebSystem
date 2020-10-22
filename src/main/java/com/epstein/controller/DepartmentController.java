package com.epstein.controller;

import com.epstein.configuration.ModelConfig;
import com.epstein.entity.Department;
import com.epstein.dto.DepartmentDTO;
import com.epstein.factory.ModelFactory;
import com.epstein.service.DepartmentService;
import com.epstein.service.RoleService;
import com.epstein.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller @RequestMapping("/departments")
public class DepartmentController {

    @Autowired private DepartmentService departmentService;
    @Autowired private UserService userService;
    @Autowired private RoleService roleService;

    @Autowired private ModelFactory modelFactory;

    @GetMapping("/get")
    public String getAll(Model model) {

        model = modelFactory.setModel(model)
                .withAllDepartments()
                .create();

        model.addAttribute("page", "departments");
        return "base";
    }

    @GetMapping("/get/{id}")
    public String getById(@PathVariable int id, Model model) {

        model = modelFactory.setModel(model)
                .withAllDepartments()
                .withUsersInDepartment(id)
                .create();

        model.addAttribute("page", "department-details");
        return "base";
    }

    @GetMapping("/get/{id}/edit")
    public String edit(@PathVariable int id, Model model) {

        model = modelFactory.setModel(model)
                .withDepartment(id)
                .withAllUsers()
                .create();

        model.addAttribute("page", "department-details-edit");
        return "base";
    }

    @PostMapping("/get/{id}/edit")
    public RedirectView postEdit(@PathVariable int id, @ModelAttribute(value="departmentForm") DepartmentDTO departmentDTO, Model model) {

        Department department = this.departmentService.getDepartmentFromForm(departmentDTO);
        this.departmentService.updateDepartmentAndSuperiorRole(department);
        return new RedirectView("/departments/get/" + id);
    }


    @GetMapping("/add")
    public String add(Model model) {

        Department department = departmentService.getSample();
        model.addAttribute("thisDepartment" , department);
        model.addAttribute("departmentForm", new DepartmentDTO());

        model = modelFactory.setModel(model)
                .withAllUsers()
                .create();

        model.addAttribute("page", "department-details-add");
        return "base";
    }

    @PostMapping("/add")
    public RedirectView postAdd(@ModelAttribute DepartmentDTO departmentDTO, Model model) {
        this.departmentService.updateDepartment( this.departmentService.getDepartmentFromForm(departmentDTO));
        return new RedirectView("/departments/get");
    }
}
