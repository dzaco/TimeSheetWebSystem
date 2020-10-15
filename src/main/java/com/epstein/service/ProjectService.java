package com.epstein.service;

import com.epstein.entity.Project;
import com.epstein.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired private ProjectRepository projectRepository;

    public Project save(Project project) {
        return this.projectRepository.save(project);
    }

    public List<Project> getProjects() {
        return this.projectRepository.findAll();
    }

    public List<Project> getActiveProjects() {
        return this.projectRepository.findAllActive();
    }

    public Project getProjectById( int id) {
        return this.projectRepository.findById(id).orElse(new Project());
    }

    public String deleteProject(int id) {
        this.projectRepository.deleteById(id);
        return "Projekt " + id + " został usunięty";
    }

    public Project update(Project project) {
        Project proj = this.getProjectById(project.getId());
        proj.setCode( project.getCode() );
        proj.setName( project.getName() );
        proj.setManager( project.getManager() );
        proj.setStartDate( project.getStartDate() );
        proj.setEndDate( project.getEndDate() );
        proj.setOther( project.getOther() );
        proj.setCode( project.getCode() );

        return this.save(proj);
    }


    public List<Project> getProjectsOfSupervisor(int userId) {
        return this.projectRepository.getProjectOfSupervisor(userId);
    }
}
