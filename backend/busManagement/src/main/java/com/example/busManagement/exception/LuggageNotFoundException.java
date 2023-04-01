package com.example.busManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class LuggageNotFoundException extends RuntimeException {

    public LuggageNotFoundException(Long id) {
        super("Could not find luggage " + id);
    }
}

@ControllerAdvice
class LuggageNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(LuggageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(LuggageNotFoundException ex) {
        return ex.getMessage();
    }
}
