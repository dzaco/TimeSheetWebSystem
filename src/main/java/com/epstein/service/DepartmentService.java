package com.epstein.service;

import com.epstein.entity.Department;
import com.epstein.dto.DepartmentDTO;
import com.epstein.entity.User;
import com.epstein.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private UserService userService;

    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(int id) {
        return departmentRepository.getOne(id);
    }

    public String deleteDepartment(int id) {
        departmentRepository.deleteById(id);
        return "Dział " + id + " został usunięty";
    }

    public Department updateDepartment(Department department) {
        Department dep = departmentRepository.findById(department.getId()).orElse(new Department());
        dep.setName( department.getName() );
        dep.setSuperior( department.getSuperior() );
        return departmentRepository.save(dep);
    }

    public List<User> getUsersInDepartment(int departmentId, int active) {
        return this.userService.getUsersInDepartment(departmentId, active);
    }

    public Department updateDepartmentAndSuperiorRole(Department department) {
        User currSuperior = userService.getUserById(this.getDepartmentById(department.getId()).getSuperiorId());
        User newSuperior = userService.getUserById(department.getSuperiorId());
        if(currSuperior.getId() != newSuperior.getId() && newSuperior.getRole().equals(RoleService.USER)) {
            newSuperior.setRole(RoleService.MANAGER);
            currSuperior.setRole(RoleService.USER);
            this.userService.updateUser(newSuperior);
            this.userService.updateUser(currSuperior);
        }
        return this.updateDepartment(department);
    }

    public Department getDepartmentFromForm(DepartmentDTO form) {
        Department department = new Department();
        department.setId(form.getId());
        department.setName(form.getName());
        department.setSuperior( this.userService.getUserById(form.getSuperiorId()));
        return department;
    }

    public List<Department> getDepartmentsOfSupervisor(int id) {
        return this.departmentRepository.getDepartmentOfSupervisor(id);
    }

    public Department getSample() {
        Department department = new Department();
        department.setId( this.departmentRepository.lastId() );
        department.setName("Nazwa");
        department.setSuperior( this.userService.getUserById(1) );
        return department;
    }
}
