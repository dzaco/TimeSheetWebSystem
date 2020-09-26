package com.epstein.repository;

import com.epstein.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    //@Query(value = "SELECT * FROM users u where u.email = :email" , nativeQuery = true)
    User findByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM USERS u WHERE u.active = 1", nativeQuery = true)
    List<User> findAllActive();

    @Query(value = "Select max(user_id) + 1 From users", nativeQuery = true)
    int lastId();

    @Query(value = "SELECT * FROM users where department_id = :departmentId and active = :active", nativeQuery = true)
    List<User> getUsersInDepartment(@Param("departmentId") int departmentId, @Param("active") int active);

    @Query(value = "Select * FROM users where user_id in (SELECT user_id FROM users_projects where project_id = :projectId)" ,nativeQuery = true)
    List<User> getUsersInProject(@Param("projectId") int projectId);


}
