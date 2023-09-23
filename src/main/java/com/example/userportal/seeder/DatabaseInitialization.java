package com.example.userportal.seeder;

import com.example.userportal.dto.UserDto;
import com.example.userportal.entity.Role;
import com.example.userportal.entity.RoleName;
import com.example.userportal.entity.User;
import com.example.userportal.repository.RoleRepository;
import com.example.userportal.repository.UserRepository;
import com.example.userportal.service.IUserService;
import com.example.userportal.service.impl.IUserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

/**
 * This class is used to initialize the database with some default values.
 * It is executed when the application starts.
 */
@Configuration
@AllArgsConstructor
public class DatabaseInitialization implements ApplicationRunner {
    private final RoleRepository roleRepository;
    private final IUserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        addRolesIntoDb();
        addDefaultAdmin();
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

    private void addDefaultAdmin(){
        User user = userService.findByEmail("admin@mail.com");
        if (user==null){
            UserDto userDto = new UserDto();
            userDto.setFirstName("Admin");
            userDto.setLastName("admin");
            userDto.setEmail("admin@mail.com");
            userDto.setBirthDate(LocalDate.now());
            userDto.setPhone("01722680407");
            userDto.setPassword("admin");
            userDto.setAddress("Dhaka");
            userDto.setRoleName(RoleName.ROLE_ADMIN);
            userService.saveUser(userDto);
        }
    }
}
