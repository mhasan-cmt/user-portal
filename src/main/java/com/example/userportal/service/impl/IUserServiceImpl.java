package com.example.userportal.service.impl;

import com.example.userportal.dto.UserDto;
import com.example.userportal.entity.Role;
import com.example.userportal.entity.User;
import com.example.userportal.repository.RoleRepository;
import com.example.userportal.repository.UserRepository;
import com.example.userportal.service.IUserService;
import com.example.userportal.util.BirthDateUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IUserServiceImpl implements IUserService {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        Role role = roleRepository.findByName(userDto.getRoleName());
        user.setRoles(List.of(role));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAddress(userDto.getAddress());
        user.setBirthDate(userDto.getBirthDate());
        userRepository.saveAndFlush(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setBirthDate(user.getBirthDate());
        userDto.setAddress(user.getAddress());
        userDto.setAge(BirthDateUtil.calculateAge(userDto.getBirthDate()));
        return userDto;
    }

    @Override
    public boolean passwordsMatch(String oldPassword, String password) {
        return passwordEncoder.matches(oldPassword, password);
    }
}
