package com.example.userportal.seeder;

import com.example.userportal.entity.Role;
import com.example.userportal.entity.RoleName;
import com.example.userportal.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

/**
 * This class is used to initialize the database with some default values.
 * It is executed when the application starts.
 */
@Configuration
@AllArgsConstructor
public class DatabaseInitialization implements ApplicationRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        addRolesIntoDb();
    }

    /**
     * Creates a role if it does not exist in the database.
     *
     * @param name the name of the role
     */
    private void createRoleIfNotFound(final RoleName name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
    }

    /**
     * Adds roles into the database.
     */
    private void addRolesIntoDb() {
        createRoleIfNotFound(RoleName.ROLE_ADMIN);
        createRoleIfNotFound(RoleName.ROLE_USER);
    }
}
