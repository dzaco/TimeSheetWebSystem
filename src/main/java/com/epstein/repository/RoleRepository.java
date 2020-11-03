package com.epstein.repository;

import com.epstein.entity.Role;
import com.epstein.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String role);

    @Query(value =
            "SELECT * FROM roles r WHERE r.role_id IN (SELECT c.role_id FROM user_role c WHERE c.user_id = :id)",
            nativeQuery = true)
    List<Role> findByUserId(int id);

}