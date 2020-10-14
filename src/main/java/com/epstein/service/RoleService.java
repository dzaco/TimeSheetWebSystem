package com.epstein.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RoleService {

    public static final String USER = "USER";
    public static final String MANAGER = "MANAGER";
    public static final String ACCOUNTANT = "ACCOUNTANT";
    public static final String CONTRACT = "CONTRACT";
    public static final String ADMIN = "ADMIN";

    public List<String> getRolesList() {
        return Arrays.asList( this.getRoles() );

    }
    public String[] getRoles() {
        return new String[]{ USER, MANAGER, ACCOUNTANT, CONTRACT, ADMIN };
    }

    public String[] getHighRoles() {
        return new String[] {MANAGER, ACCOUNTANT, ADMIN};
    }
    public List<String> getHighRolesList() {
        return Arrays.asList( getHighRoles() );
    }


}
