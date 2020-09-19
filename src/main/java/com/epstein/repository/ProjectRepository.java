package com.epstein.repository;

import com.epstein.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    @Query(value = "SELECT * FROM projects where end_date > now() OR end_date is null", nativeQuery = true)
    List<Project> findAllActive();
}
