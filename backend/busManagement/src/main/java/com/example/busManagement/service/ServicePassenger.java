package com.example.busManagement.service;

import com.example.busManagement.domain.DTO.PassengerAverageLugWeightDTO;
import com.example.busManagement.domain.DTO.PassengerDTO;
import com.example.busManagement.domain.Luggage;
import com.example.busManagement.domain.Passenger;
import com.example.busManagement.exception.PassengerNotFoundException;
import com.example.busManagement.repository.IRepositoryLuggage;
import com.example.busManagement.repository.IRepositoryPassenger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServicePassenger {
    private final IRepositoryPassenger passenger_repository;
    private final IRepositoryLuggage luggage_repository;

    public ServicePassenger(IRepositoryPassenger passenger_repository, IRepositoryLuggage luggage_repository) {
        this.passenger_repository = passenger_repository;
        this.luggage_repository = luggage_repository;
    }

    public List<PassengerDTO> getAllPassengers() {
        ModelMapper modelMapper = new ModelMapper();
        List<Passenger> passengers = passenger_repository.findAll();
        return passengers.stream()
                .map(passenger -> modelMapper.map(passenger, PassengerDTO.class))
                .collect(Collectors.toList());
    }

    public Passenger getByIdPassenger(String id) {
        Long passengerId = Long.parseLong(id);
        return passenger_repository.findById(passengerId)
                .orElseThrow(() -> new PassengerNotFoundException(passengerId));
    }

    public Passenger addPassenger(Passenger newPassenger, Long luggageID) {
        Luggage luggage = this.luggage_repository.findById(luggageID).get();
        Set<Luggage> luggageList = new HashSet<>();
        luggageList.add(luggage);               // add given luggage ID to LuggageList
        newPassenger.setLuggages(luggageList);  // Add to Passenger the 'ListofLuggages'

        luggage.setPassenger(newPassenger);
        return passenger_repository.save(newPassenger);
    }

    public Passenger updatePassenger(Passenger newPassenger, Long id) {
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

    public void deletePassenger(Long id) {
        passenger_repository.deleteById(id);
    }

    public List<PassengerAverageLugWeightDTO> getPassengersOrdereddByAverageLuggageWeight() {
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
}
