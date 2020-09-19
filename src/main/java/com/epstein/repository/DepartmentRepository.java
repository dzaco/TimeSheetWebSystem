package com.epstein.repository;

import com.epstein.entity.Department;
import com.epstein.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Integer> {

    @Query(value = "SELECT * FROM departments where superior_id = :id" , nativeQuery = true)
    Department getDepartmentOfSupervisor(@Param("id") int id);

    @Query(value = "Select max(department_id) + 1 From departments", nativeQuery = true)
    int lastId();

}
