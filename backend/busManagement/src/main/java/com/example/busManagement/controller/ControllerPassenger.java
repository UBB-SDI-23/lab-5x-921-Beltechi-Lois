package com.example.busManagement.controller;

import com.example.busManagement.domain.*;
import com.example.busManagement.exception.PassengerNotFoundException;
import com.example.busManagement.repository.IRepositoryLuggage;
import com.example.busManagement.repository.IRepositoryPassenger;
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

    private final IRepositoryPassenger passenger_repository;
    private final IRepositoryLuggage luggage_repository;

    ControllerPassenger(IRepositoryPassenger passenger_repository, IRepositoryLuggage luggage_repository) {
        this.passenger_repository = passenger_repository;
        this.luggage_repository = luggage_repository;
    }


    @GetMapping("/passengers")  //GETALL fara luggages
    @CrossOrigin(origins = "*")
    List<PassengerDTO> all() {

        ModelMapper modelMapper = new ModelMapper();
        List<Passenger> passengers = passenger_repository.findAll();
        return passengers.stream()
                .map(passenger -> modelMapper.map(passenger, PassengerDTO.class))
                .collect(Collectors.toList());

    }

    @GetMapping("/passengers/{id}")     //GET BY ID, cu LUGGAGES
    @CrossOrigin(origins = "*")
    Passenger one(@PathVariable Long id) {

        return passenger_repository.findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(id));
    }

    // /luggages/{luggageID}/passengers -> /passengers/luggages/{luggageID}
    // New passenger. Add existing luggages
    @PostMapping("/passengers/luggages/{luggageID}")   // ADD
    Passenger newPassenger(@Valid @RequestBody Passenger newPassenger,@PathVariable Long luggageID)
    {
        Luggage luggage = this.luggage_repository.findById(luggageID).get();
        Set<Luggage> luggageList = new HashSet<>();
        luggageList.add(luggage); // add given luggage ID to LuggageList
        newPassenger.setLuggages(luggageList); // Add to Passenger the ListofLuggages

        luggage.setPassenger(newPassenger);
        return passenger_repository.save(newPassenger);
    }


    @PutMapping("/passengers/{id}")     //UPDATE
    Passenger replacePassenger(@Valid @RequestBody Passenger newPassenger, @PathVariable Long id) {

        return passenger_repository.findById(id)
                .map(Passenger -> {
                    Passenger.setTimesTravelled(newPassenger.getTimesTravelled());
                    Passenger.setFirstName(newPassenger.getFirstName());
                    Passenger.setLastName(newPassenger.getLastName());
                    Passenger.setDateOfBirth(newPassenger.getDateOfBirth());
                    Passenger.setGender(newPassenger.getGender());
                    Passenger.setPhoneNumber(newPassenger.getPhoneNumber());
                    return passenger_repository.save(Passenger);
                })
                .orElseGet(() -> { // otherwise if not found, ADD IT
                    newPassenger.setId(id);
                    return passenger_repository.save(newPassenger);
                });
    }

    @DeleteMapping("/passengers/{id}")  //DELETE
    void deletePassenger(@PathVariable Long id) {
        passenger_repository.deleteById(id);
    }

    @GetMapping("/passengers/average-luggageWeight-of-passenger") // A3 extra statistic
    @CrossOrigin(origins = "*")
    public List<PassengerAverageLugWeightDTO> getPassengersOrderedByAverageLuggageWeight() {
        List<PassengerAverageLugWeightDTO> result = new ArrayList<>();

        List<Passenger> passengers = passenger_repository.findAll();

        for (Passenger passenger : passengers) {
            double totalDistance = 0;
            int luggageCount = 0;

            // Find for current Passenger all its luggages and count them
            for (Luggage luggage : luggage_repository.findAll()) {
                if (luggage.getPassenger().getId() == passenger.getId()) {  //luggage.getid
                    int distanceStr=luggage.getWeight();
                    double weight = Double.parseDouble(String.valueOf(distanceStr));
                    totalDistance += weight;
                    luggageCount++;

                }
            }

            if (luggageCount > 0) {
                double averageWeight = totalDistance / luggageCount;
                PassengerAverageLugWeightDTO dto = new PassengerAverageLugWeightDTO(
                        passenger.getId(), passenger.getFirstName(), passenger.getLastName(), averageWeight);
                result.add(dto);
            }
        }

        result.sort(Comparator.comparingDouble(PassengerAverageLugWeightDTO::getAverageLuggageWeight)
                .thenComparingLong(PassengerAverageLugWeightDTO::getId));

        //
        //If a passenger doesn't have any luggages associated with them,
        //the loop won't find any luggages that match their ID, and therefore won't
        //calculate the average luggage weight for that passenger.
        // -> only 6 passengers have associated luggages; check that on Luggage table
        return result;
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

