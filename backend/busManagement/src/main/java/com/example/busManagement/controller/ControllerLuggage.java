package com.example.busManagement.controller;

import com.example.busManagement.domain.*;
import com.example.busManagement.domain.DTO.LuggageDTO;
import com.example.busManagement.domain.DTO.LuggageDTOWithId;
import com.example.busManagement.exception.LuggageNotFoundException;
import com.example.busManagement.repository.IRepositoryLuggage;
import com.example.busManagement.repository.IRepositoryPassenger;
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

    private final IRepositoryLuggage luggage_repository;
    private final IRepositoryPassenger passenger_repository;


    public ControllerLuggage(IRepositoryLuggage repository, IRepositoryPassenger passenger_repository) {
        this.luggage_repository = repository;
        this.passenger_repository = passenger_repository;
    }


    //GETALL without passengers, only with ID PASSENGER[ 1: Passenger]
    @GetMapping("/luggages")
    @CrossOrigin(origins = "*")
    List<LuggageDTOWithId> all() {

        ModelMapper modelMapper= new ModelMapper();

        //Set for each Luggage its own Passenger's id
        modelMapper.typeMap(Luggage.class, LuggageDTOWithId.class)
                .addMapping(luggage ->
                        luggage.getPassenger().getId(), LuggageDTOWithId::setPassenger_Id);

        // not working :\
        //modelMapper.typeMap(Luggage.class, LuggageDTOWithId.class);

        return luggage_repository.findAll().stream()
                .map(luggage -> modelMapper.map(luggage, LuggageDTOWithId.class))
                .collect(Collectors.toList());

        //old version:
        //return repository.findAll().stream().map(m->m.toLuggageDTOWithId()).collect(Collectors.toList());
    }

    //GET BY ID, cu passengers
    @GetMapping("/luggages/{id}")
    @CrossOrigin(origins = "*")
    LuggageDTO one(@PathVariable Long id) {
        //return repository.findById(id).get().toLuggageDTO();

        if (luggage_repository.findById(id).isEmpty())
            throw new LuggageNotFoundException(id);

        ModelMapper modelMapper = new ModelMapper();
        LuggageDTO luggageDTO = modelMapper.map(luggage_repository.findById(id).get(), LuggageDTO.class);
        return luggageDTO;
    }

    // A2 FILTER
    @GetMapping("/luggages/higherThanGivenWeight/{value}")
    @CrossOrigin(origins = "*")
    public List<Luggage> higherThan(@PathVariable int value) {
        return luggage_repository.findAll()
                .stream()
                .filter(luggage -> luggage.getWeight() > value)
                .collect(Collectors.toList());
    }

    @PostMapping("/luggages")   // ADD
    Luggage newLuggage(@Valid @RequestBody Luggage newLuggage) {
        return luggage_repository.save(newLuggage);
    }


    @PutMapping("/luggages/{luggageID}/passengers/{passengerID}")     //UPDATE
    Luggage replaceLuggage(@Valid @RequestBody Luggage luggage, @PathVariable Long luggageID, @PathVariable Long passengerID)
    {
        Passenger passenger = passenger_repository.findById(passengerID).get();
        Luggage foundLuggage = this.luggage_repository.findById(luggageID).get();

        foundLuggage.setStatus(luggage.getStatus());
        foundLuggage.setSize(luggage.getSize());
        foundLuggage.setWeight(luggage.getWeight());
        foundLuggage.setOwner(luggage.getOwner());
        foundLuggage.setBusNumber(luggage.getBusNumber());
        foundLuggage.setPassenger(passenger);
        return this.luggage_repository.save(foundLuggage);
    }


    // Luggage ID 3 deleted;   Passenger -> delete this luggage here too;  Delete then luggage;
    // /passengers/{passengerID}/luggages/{luggageID} -> /luggages/{luggageID}/passengers/{passengerID}
    @DeleteMapping("/luggages/{luggageID}/passengers/{passengerID}")  //DELETE
    void deleteLuggage(@PathVariable Long luggageID, @PathVariable Long passengerID)
    {
        Passenger passenger= passenger_repository.findById(passengerID).get();
        Luggage luggage = luggage_repository.findById(luggageID).get();

        // Remove the luggage from the passenger's list of luggages
        ///passenger.getLuggages().remove(luggage); // Remove from list ;; Cascading already does this

        // Set the Luggage's passenger to null
        //luggage.setPassenger(null);

        this.luggage_repository.deleteById(luggageID);
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
