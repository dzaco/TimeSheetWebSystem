package com.epstein.controller;

import com.epstein.entity.Department;
import com.epstein.entity.DepartmentForm;
import com.epstein.model.DepartmentListDTO;
import com.epstein.service.DepartmentService;
import com.epstein.service.RoleService;
import com.epstein.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@Controller @RequestMapping("/departments")
public class DepartmentController extends IController {

    @Autowired private DepartmentService departmentService;
    @Autowired private UserService userService;
    @Autowired private RoleService roleService;

    @GetMapping("/get")
    public String getAll(Model model) {
        model.addAttribute("departments", departmentService.getDepartments());
        this.mainAttribute(model);

        model.addAttribute("page", "departments");
        return "base";
    }

    @GetMapping("/get/{id}")
    public String getById(@PathVariable int id, Model model) {
        Department department = departmentService.getDepartmentById(id);
        model.addAttribute("department", department);
        model.addAttribute("users", departmentService.getUsersInDepartment(department.getId(), 1));
        model.addAttribute("userHeader", "Lista Pracowników w Dziale");

        this.mainAttribute(model);

        model.addAttribute("page", "department-details");
        return "base";
    }

    @GetMapping("/get/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("thisDepartment", departmentService.getDepartmentById(id));      // edytowany wydział
        model.addAttribute("users", userService.getUsers() );    // lista pracowników w tym wydziale - możliwość zostania dyrektorem

        this.mainAttribute(model);

        model.addAttribute("page", "department-details-edit");
        return "base";
    }

    @PostMapping("/get/{id}/edit")
    public RedirectView postEdit(@PathVariable int id, @ModelAttribute(value="departmentForm") DepartmentForm departmentForm, Model model) {
        this.mainAttribute(model);

        Department department = this.departmentService.getDepartmentFromForm(departmentForm);
        this.departmentService.updateDepartmentAndSuperiorRole(department);
        return new RedirectView("/departments/get/" + id);
    }


    @GetMapping("/add")
    public String add(Model model) {
        this.mainAttribute(model);

        Department department = departmentService.getSample();
        model.addAttribute("thisDepartment" , department);
        model.addAttribute("departmentForm", new DepartmentForm());
        model.addAttribute("users", this.userService.getUsers() );
        model.addAttribute("page", "department-details-add");
        return "base";
    }

    @PostMapping("/add")
    public RedirectView postAdd(@ModelAttribute DepartmentForm departmentForm, Model model) {
        this.mainAttribute(model);

        this.departmentService.updateDepartment( this.departmentService.getDepartmentFromForm(departmentForm));
        return new RedirectView("/departments/get");
    }
}
