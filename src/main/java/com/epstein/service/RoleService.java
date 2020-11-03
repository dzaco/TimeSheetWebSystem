package com.epstein.service;

import com.epstein.entity.Role;
import com.epstein.model.Roles;
import com.epstein.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class RoleService {

    @Autowired private RoleRepository roleRepository;

    public Role USER() {
        return this.roleRepository.findByRole("USER");
    }
    public Role MANAGER() {
        return this.roleRepository.findByRole("MANAGER");
    }
    public Role ACCOUNTANT() {
        return this.roleRepository.findByRole("ACCOUNTANT");
    }
    public Role CONTRACT() {
        return this.roleRepository.findByRole("CONTRACT");
    }
    public Role ADMIN() {
        return this.roleRepository.findByRole("ADMIN");
    }

    public Roles getRoles() {
        return new Roles( roleRepository.findAll() );
    }

    public Roles getHighRoles() {
        Collection<Role> collection = Arrays.asList(MANAGER(), ACCOUNTANT(), ADMIN());
        return new Roles(collection);
    }


}
