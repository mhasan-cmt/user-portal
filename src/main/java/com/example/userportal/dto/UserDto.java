package com.example.userportal.dto;

import com.example.userportal.entity.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private Long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty(message = "Please enter your Email")
    @Email
    private String email;
    @NotEmpty(message = "Please enter your Address")
    private String address;
    private LocalDate birthDate;
    @NotEmpty(message = "Please enter your Password")
    private String password;
    private RoleName roleName;
    @NotEmpty(message = "Please enter your Phone Number")
    private String phone;
    private String confirmPassword;
    private int age;
}
