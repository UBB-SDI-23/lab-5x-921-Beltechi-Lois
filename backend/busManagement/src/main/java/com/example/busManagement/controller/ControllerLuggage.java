package com.example.busManagement.controller;

import com.example.busManagement.domain.*;
import com.example.busManagement.domain.DTO.LuggageDTO;
import com.example.busManagement.domain.DTO.LuggageDTOWithId;
import com.example.busManagement.exception.LuggageNotFoundException;
import com.example.busManagement.repository.IRepositoryLuggage;
import com.example.busManagement.repository.IRepositoryPassenger;
import com.example.busManagement.service.ServiceLuggage;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ControllerLuggage {

    private final ServiceLuggage ControllerLuggage;



    public ControllerLuggage(ServiceLuggage controllerLuggage) {
        ControllerLuggage = controllerLuggage;
    }


    //GETALL without passengers, only with ID PASSENGER[ 1: Passenger]
    @GetMapping("/luggages")
    @CrossOrigin(origins = "*")
    List<LuggageDTOWithId> all() {

        return ControllerLuggage.getAllLuggages();
    }

    //GET BY ID, cu passengers
    @GetMapping("/luggages/{id}")
    @CrossOrigin(origins = "*")
    LuggageDTO one(@PathVariable Long id) {
        return ControllerLuggage.getByIdLuggage(id);
    }

    // A2 FILTER
    @GetMapping("/luggages/higherThanGivenWeight/{value}")
    @CrossOrigin(origins = "*")
    public List<Luggage> higherThan(@PathVariable int value) {
        return ControllerLuggage.higherThanWeight(value);
    }

    @PostMapping("/luggages")   // ADD
    Luggage newLuggage(@Valid @RequestBody Luggage newLuggage) {

        return ControllerLuggage.addNewLuggage(newLuggage);
    }


    @PutMapping("/luggages/{luggageID}/passengers/{passengerID}")     //UPDATE
    Luggage replaceLuggage(@Valid @RequestBody Luggage luggage, @PathVariable Long luggageID, @PathVariable Long passengerID)
    {
        return ControllerLuggage.updateLuggage(luggage,luggageID,passengerID);
    }


    // Luggage ID 3 deleted;   Passenger -> delete this luggage here too;  Delete then luggage;
    // /passengers/{passengerID}/luggages/{luggageID} -> /luggages/{luggageID}/passengers/{passengerID}
    @DeleteMapping("/luggages/{luggageID}/passengers/{passengerID}")  //DELETE
    void deleteLuggage(@PathVariable Long luggageID, @PathVariable Long passengerID)
    {
        ControllerLuggage.deleteLuggage(luggageID,passengerID);
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
