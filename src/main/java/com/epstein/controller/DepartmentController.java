package com.epstein.controller;

import com.epstein.entity.Department;
import com.epstein.entity.DepartmentForm;
import com.epstein.service.DepartmentService;
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

    @GetMapping("/get")
    public String getDepartments(Model model) {
        model.addAttribute("departments", departmentService.getDepartments());
        model.addAttribute("page", "departments");
        return "base";
    }

    @GetMapping("/get/{id}")
    public String getDepartmentById(@PathVariable int id, Model model) {
        Department department = departmentService.getDepartmentById(id);
        model.addAttribute("department", department);
        model.addAttribute("users", departmentService.getUsersInDepartment(department.getId(), 1));
        model.addAttribute("userHeader", "Lista Pracowników w Dziale");
        model.addAttribute("page", "department-details");
        return "base";
    }

    @GetMapping("/get/{id}/edit")
    public String getEditDepartmentById(@PathVariable int id, Model model) {
        model.addAttribute("thisDepartment", departmentService.getDepartmentById(id));      // edytowany wydział
        //model.addAttribute("departments", departmentService.getDepartments());              // lista wszystkich Wydziałów
        model.addAttribute("users", departmentService.getUsersInDepartment(id,1));    // lista pracowników w tym wydziale - możliwość zostania dyrektorem
        model.addAttribute("page", "department-details-edit");
        return "base";
    }

    @PostMapping("/get/{id}/edit")
    public String postEditDepartment(@PathVariable int id, @ModelAttribute(value="departmentForm") DepartmentForm departmentForm, Model model) {
        Department department = this.departmentService.getDepartmentFromForm(departmentForm);
        this.departmentService.updateDepartmentAndSuperiorRole(department);
        return this.getDepartmentById(id,model);
    }

    @GetMapping("/add")
    public String addDepartment(Model model) {
        Department department = departmentService.getSample();
        model.addAttribute("thisDepartment" , department);
        model.addAttribute("departmentForm", new DepartmentForm());
        model.addAttribute("users", this.userService.getUsers() );
        model.addAttribute("page", "department-details-add");
        return "base";
    }

    @PostMapping("/add")
    public RedirectView postAddingDepartment(@ModelAttribute DepartmentForm departmentForm, Model model) {
        this.departmentService.updateDepartment( this.departmentService.getDepartmentFromForm(departmentForm));
        return new RedirectView("/departments/get");
    }
}
