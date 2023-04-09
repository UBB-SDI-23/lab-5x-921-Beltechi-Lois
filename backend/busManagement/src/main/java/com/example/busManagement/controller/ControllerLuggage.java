package com.example.busManagement.controller;

import com.example.busManagement.domain.*;
import com.example.busManagement.domain.DTO.LuggageDTO;
import com.example.busManagement.domain.DTO.LuggageDTOWithId;
import com.example.busManagement.domain.DTO.LuggagePersonDTO;
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

@RestController
public class ControllerLuggage {

    private final ServiceLuggage ControllerLuggage;


    public ControllerLuggage(ServiceLuggage controllerLuggage) {
        ControllerLuggage = controllerLuggage;
    }


    //GETALL without people, only with PeopleID
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


    @PostMapping("/luggages/people/{personID}")   // ADD existing PersonID to a new Luggage
    Luggage newLuggage(@Valid @RequestBody Luggage newLuggage,@PathVariable Long personID) {

        return ControllerLuggage.addNewLuggage(newLuggage,personID);
    }


    @PutMapping("/luggages/{luggageID}/people/{personID}")     //UPDATE
    Luggage replaceLuggage(@Valid @RequestBody Luggage luggage, @PathVariable Long luggageID, @PathVariable Long personID)
    {
        return ControllerLuggage.updateLuggage(luggage,luggageID,personID);
    }


    // Luggage ID 3 deleted;   Person -> delete this luggage here too (cascading will do this);
    @DeleteMapping("/luggages/{luggageID}")  //DELETE
    void deleteLuggage(@PathVariable Long luggageID)
    {
        ControllerLuggage.deleteLuggage(luggageID);
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

    @GetMapping("/luggage/color/blue/people-count")
    public List<LuggagePersonDTO> getBlueLuggageCount() {
            return ControllerLuggage.getBlueLuggageCount();
    }

}
