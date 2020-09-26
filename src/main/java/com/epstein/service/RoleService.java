package com.epstein.service;

import com.epstein.entity.User;
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

    public static List<String> getRoles() {
        return Arrays.asList(
                "USER","MANAGER", "ACCOUNTANT", "CONTRACT", "ADMIN"
        );

    }
    public static String[] getRolesArray() {
        return new String[]{"USER","MANAGER", "ACCOUNTANT", "CONTRACT", "ADMIN"};
    }

    public static String[] getHiRole() {
        return new String[] {MANAGER, ACCOUNTANT, ADMIN};
    }
}
