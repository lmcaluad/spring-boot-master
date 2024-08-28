package com.curso.master.service.validator;

import com.curso.master.exception.InvalidPasswordException;
import org.springframework.util.StringUtils;

public class PasswordValidator {

    public static void validatePassword(
            String password,
            String passwordRepeated
    ) {
        if (!StringUtils.hasText(password) || !StringUtils.hasText(passwordRepeated))
            throw new IllegalArgumentException("Passwords must contains data");

        if (!password.equals(passwordRepeated))
            throw new InvalidPasswordException(password, passwordRepeated, "Password do not match");

        if (!containsNumber(password))
            throw new InvalidPasswordException(password, "Password must contain at least one number");

        if (!containsUpperCase(password))
            throw new InvalidPasswordException(password, "Password must contain at least one uppercase letter");

        if (!containsLowerCase(password))
            throw new InvalidPasswordException(password, "Password must contain at least one lowercase letter");

        if (!containsSpecialCharacter(password))
            throw new InvalidPasswordException(password, "Password must contain at least one special character");

    }

    //	REGEX: [!@#$%^&*()_+\-=\[\]{};':",.<>/?]
    //	REGEX DENTRO DE VALIDACIÓN: password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?].*")

    private static boolean containsSpecialCharacter(String password) {
        return password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?].*");
    }

    private static boolean containsLowerCase(String password) {
        return password.matches(".*[a-z].*");
    }

    private static boolean containsUpperCase(String password) {
        return password.matches(".*[A-Z].*");
    }

    private static boolean containsNumber(String password) {
        return password.matches(".*\\d.*");
    }
}
