package com.example.userportal.repository;

import com.example.userportal.entity.Role;
import com.example.userportal.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface is used to interact with the database for Roles.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * This method is used to find a role by name.
     *
     * @param name the name of the role.
     * @return a role.
     */
    Role findByName(RoleName name);
}
