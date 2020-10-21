package com.epstein.configuration;


import com.epstein.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.management.relation.Role;

public class ModelConfig {

    public boolean withAddButton = false;
    public boolean withUserList = false;
    public boolean withTimesheetList = false;


    public ModelConfig() {    }

    public ModelConfig withAddButton(boolean flag) {
        this.withAddButton = flag;
        return this;
    }
    public ModelConfig withUserList(boolean flag) {
        this.withUserList = flag;
        return this;
    }
    public ModelConfig withTimesheetList(boolean flag) {
        this.withTimesheetList = flag;
        return this;
    }


}
