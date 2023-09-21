package com.example.userportal.service;

import com.example.userportal.dto.UserDto;
import com.example.userportal.entity.User;

import java.util.List;

/**
 * This interface defines the methods that will be used to manage users.
 */
public interface IUserService {
    /**
     * This method is used to save a user.
     * @param userDto The user to be saved.
     */
    void saveUser(UserDto userDto);

    /**
     * This method is used to update a user.
     * @param user The user to be updated.
     */
    void update(User user);

    /**
     * This method is used to find a user by email
     * @param email The email of the user to be found.
     *              
     */
    User findByEmail(String email);
    /**
     * This method is used to find all users.
     * @param id The id of the user to be found.
     */
    List<UserDto> findAllUsers();

    Boolean existsByEmail(String email);

    User findById(Long id);

    UserDto convertEntityToDto(User user);

    boolean passwordsMatch(String oldPassword, String password);
}
