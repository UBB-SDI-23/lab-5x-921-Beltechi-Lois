package com.example.busManagement.controller;

import com.example.busManagement.domain.*;
import com.example.busManagement.domain.DTO.LuggageDTO;
import com.example.busManagement.domain.DTO.LuggageDTOWithId;
import com.example.busManagement.domain.DTO.LuggagePersonDTO;
import com.example.busManagement.domain.DTO.LuggagePersonNationalityDTO;
import com.example.busManagement.service.ServiceLuggage;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
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
    @GetMapping("/api/luggages/page/{page}/size/{size}")
    @CrossOrigin(origins = "*")
    List<LuggageDTOWithId> all(@PathVariable int page, @PathVariable int size) {
        PageRequest pr = PageRequest.of(page,size);
        return ControllerLuggage.getAllLuggages(pr);
    }

    //GET BY ID, cu passengers
    @GetMapping("/api/luggages/{id}")
    @CrossOrigin(origins = "*")
    LuggageDTO one(@PathVariable String id) {
        return ControllerLuggage.getByIdLuggage(id);
    }


    //SELECT COUNT(*) FROM person p WHERE p.nationality='American'
    // A3 statistic #2 requirement
    @GetMapping("/api/luggages/american-nationality/page/{page}/size/{size}")
    public List<LuggagePersonNationalityDTO> getAmericanLuggages(@PathVariable int page, @PathVariable int size) {
        PageRequest pr = PageRequest.of(page,size);
        return ControllerLuggage.getAmericanLuggages(pr);
    }

    @GetMapping("/api/luggages/count")
    @CrossOrigin(origins = "*")
    long count() {
        return ControllerLuggage.getCount();
    }


    @PostMapping("/api/luggages/people/{personID}")   // ADD existing PersonID to a new Luggage
    Luggage newLuggage(@Valid @RequestBody Luggage newLuggage,@PathVariable Long personID) {

        return ControllerLuggage.addNewLuggage(newLuggage,personID);
    }

    @PutMapping("/api/luggages/{luggageID}/person/{personID}")     //UPDATE
    Luggage replaceLuggage(@Valid @RequestBody Luggage luggage, @PathVariable Long luggageID,@PathVariable Long personID)
    {
        return ControllerLuggage.updateLuggage(luggage,luggageID,personID);
    }


//    @PutMapping("/api/luggages/{luggageID}/people/{personID}")     //UPDATE
//    Luggage replaceLuggage(@Valid @RequestBody Luggage luggage, @PathVariable Long luggageID, @PathVariable Long personID)
//    {
//        return ControllerLuggage.updateLuggage(luggage,luggageID,personID);
//    }


    // Luggage ID 3 deleted;   Person -> delete this luggage here too (cascading will do this);
    @DeleteMapping("/api/luggages/{luggageID}")  //DELETE
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



}
