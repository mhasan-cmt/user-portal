package com.example.userportal.util;

import java.time.LocalDate;

public class BirthDateUtil {
    public static int calculateAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        if (birthDate != null) {
            return currentDate.getYear() - birthDate.getYear();
        } else {
            return 0;
        }
    }
}
