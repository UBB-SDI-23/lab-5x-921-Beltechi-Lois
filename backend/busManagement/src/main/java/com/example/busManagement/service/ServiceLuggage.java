package com.example.busManagement.service;

import com.example.busManagement.domain.DTO.LuggageDTO;
import com.example.busManagement.domain.DTO.LuggageDTOWithId;
import com.example.busManagement.domain.Luggage;
import com.example.busManagement.domain.Passenger;
import com.example.busManagement.exception.LuggageNotFoundException;
import com.example.busManagement.repository.IRepositoryLuggage;
import com.example.busManagement.repository.IRepositoryPassenger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceLuggage {
    private final IRepositoryLuggage luggage_repository;
    private final IRepositoryPassenger passenger_repository;

    public ServiceLuggage(IRepositoryLuggage luggage_repository, IRepositoryPassenger passenger_repository) {
        this.luggage_repository = luggage_repository;
        this.passenger_repository = passenger_repository;
    }

    public List<LuggageDTOWithId> getAllLuggages() {
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

    public LuggageDTO getByIdLuggage(Long id) {
        //return repository.findById(id).get().toLuggageDTO();

        if (luggage_repository.findById(id).isEmpty())
            throw new LuggageNotFoundException(id);

        ModelMapper modelMapper = new ModelMapper();
        LuggageDTO luggageDTO = modelMapper.map(luggage_repository.findById(id).get(), LuggageDTO.class);
        return luggageDTO;
    }

    public List<Luggage> higherThanWeight(int value) {
        return luggage_repository.findAll()
                .stream()
                .filter(luggage -> luggage.getWeight() > value)
                .collect(Collectors.toList());
    }

    public Luggage addNewLuggage(Luggage newLuggage) {
        return luggage_repository.save(newLuggage);
    }

    public Luggage updateLuggage(Luggage luggage, Long luggageID, Long passengerID) {
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

    public void deleteLuggage(Long luggageID, Long passengerID) {
        Passenger passenger= passenger_repository.findById(passengerID).get();
        Luggage luggage = luggage_repository.findById(luggageID).get();

        // Remove the luggage from the passenger's list of luggages
        ///passenger.getLuggages().remove(luggage); // Remove from list ;; Cascading already does this

        // Set the Luggage's passenger to null
        //luggage.setPassenger(null);

        this.luggage_repository.deleteById(luggageID);
    }
}
