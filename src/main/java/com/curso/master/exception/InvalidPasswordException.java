package com.curso.master.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPasswordException extends RuntimeException {

    private final String password;
    private final String passwordRepeated;
    private final String errorDescription;

    public InvalidPasswordException(String password, String errorDescription) {
        this.password = password;
        this.passwordRepeated = password;
        this.errorDescription = errorDescription;
    }

    public InvalidPasswordException(String password, String passwordRepeated, String errorDescription) {
        this.password = password;
        this.passwordRepeated = passwordRepeated;
        this.errorDescription = errorDescription;
    }

    @Override
    public String getMessage() {
        return "Invalid Password: " + errorDescription;
    }

}
