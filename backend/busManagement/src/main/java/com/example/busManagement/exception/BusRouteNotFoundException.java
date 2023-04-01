package com.example.busManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BusRouteNotFoundException extends RuntimeException {

    public BusRouteNotFoundException(Long id) {
        super("Could not find BusRoute " + id);
    }
}

@ControllerAdvice
class BusNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(BusRouteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(BusRouteNotFoundException ex) {
        return ex.getMessage();
    }
}
