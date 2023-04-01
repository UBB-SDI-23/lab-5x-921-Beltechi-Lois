package com.example.busManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(Long id) {
        super("Could not find ticket " + id);
    }
}

@ControllerAdvice
class TicketNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(TicketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(TicketNotFoundException ex) {
        return ex.getMessage();
    }
}
