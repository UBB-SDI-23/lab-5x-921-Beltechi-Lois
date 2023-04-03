package com.example.busManagement.controller;

import com.example.busManagement.domain.*;
import com.example.busManagement.domain.DTO.PassengerAverageLugWeightDTO;
import com.example.busManagement.domain.DTO.PassengerDTO;
import com.example.busManagement.exception.PassengerNotFoundException;
import com.example.busManagement.repository.IRepositoryLuggage;
import com.example.busManagement.repository.IRepositoryPassenger;
import com.example.busManagement.repository.IRepositoryPerson;
import com.example.busManagement.service.ServicePassenger;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
public
class ControllerPassenger {

    private final ServicePassenger ControllerPassenger;


    ControllerPassenger(ServicePassenger controllerPassenger) {
        ControllerPassenger = controllerPassenger;
    }


    //GETALL without luggages
    @GetMapping("/passengers")
    @CrossOrigin(origins = "*")
    List<PassengerDTO> all() {

        return ControllerPassenger.getAllPassengers();

    }

    //GET BY ID, with LUGGAGES
    @GetMapping("/passengers/{id}")
    @CrossOrigin(origins = "*")
    Passenger one(@PathVariable String id) {

        return ControllerPassenger.getByIdPassenger(id);
    }


    // New passenger. Add existing luggage to it.
    @PostMapping("/passengers/luggage/{luggageID}")   // ADD
    Passenger newPassenger(@Valid @RequestBody Passenger newPassenger,@PathVariable Long luggageID)
    {
        return ControllerPassenger.addPassenger(newPassenger,luggageID);
    }


    @PutMapping("/passengers/{id}")     //UPDATE
    Passenger replacePassenger(@Valid @RequestBody Passenger newPassenger, @PathVariable Long id) {

        return ControllerPassenger.updatePassenger(newPassenger,id);
    }

    @DeleteMapping("/passengers/{id}")  //DELETE
    void deletePassenger(@PathVariable Long id) {


        ControllerPassenger.deletePassenger(id);
    }

    // A3 extra statistic
    @GetMapping("/passengers/average-luggageWeight-of-passenger")
    @CrossOrigin(origins = "*")
    public List<PassengerAverageLugWeightDTO> getPassengersOrderedByAverageLuggageWeight() {
        return ControllerPassenger.getPassengersOrdereddByAverageLuggageWeight();
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }




}

