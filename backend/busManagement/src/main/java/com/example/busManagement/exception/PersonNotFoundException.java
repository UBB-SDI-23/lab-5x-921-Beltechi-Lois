package com.example.busManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(Long id) {
        super("Could not find person " + id);
    }
}

@ControllerAdvice
class PersonNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(PersonNotFoundException ex) {
        return ex.getMessage();
    }
}
