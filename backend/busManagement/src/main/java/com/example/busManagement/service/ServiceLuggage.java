package com.example.busManagement.service;

import com.example.busManagement.domain.DTO.LuggageDTO;
import com.example.busManagement.domain.DTO.LuggageDTOWithId;
import com.example.busManagement.domain.DTO.LuggagePersonDTO;
import com.example.busManagement.domain.Luggage;
import com.example.busManagement.domain.Person;
import com.example.busManagement.exception.LuggageNotFoundException;
import com.example.busManagement.repository.IRepositoryLuggage;
import com.example.busManagement.repository.IRepositoryPerson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ServiceLuggage {
    private final IRepositoryLuggage luggage_repository;
    private final IRepositoryPerson person_repository;

    public ServiceLuggage(IRepositoryLuggage luggage_repository, IRepositoryPerson person_repository) {
        this.luggage_repository = luggage_repository;
        this.person_repository = person_repository;
    }

    public List<LuggageDTOWithId> getAllLuggages() {

        ModelMapper modelMapper= new ModelMapper();

        //Set for each Luggage its own Passenger's id
        modelMapper.typeMap(Luggage.class, LuggageDTOWithId.class)
                .addMapping(luggage ->
                        luggage.getPerson().getId(), LuggageDTOWithId::setPerson_Id);

        return luggage_repository.findAll().stream()
                .map(luggage -> modelMapper.map(luggage, LuggageDTOWithId.class))
                .collect(Collectors.toList());
    }

    public LuggageDTO getByIdLuggage(Long id) {

        if (luggage_repository.findById(id).isEmpty())
            throw new LuggageNotFoundException(id);

        ModelMapper modelMapper = new ModelMapper();
        LuggageDTO luggageDTO = modelMapper.map(luggage_repository.findById(id).get(), LuggageDTO.class);
        return luggageDTO;
    }

    public Luggage addNewLuggage(Luggage newLuggage, Long personID) {
        Person person = this.person_repository.findById(personID).get();
        newLuggage.setPerson(person);
        return luggage_repository.save(newLuggage);
    }

    public Luggage updateLuggage(Luggage luggage, Long luggageID, Long personID) {
        Person person = person_repository.findById(personID).get();
        Luggage foundLuggage = this.luggage_repository.findById(luggageID).get();

        foundLuggage.setStatus(luggage.getStatus());
        foundLuggage.setType(luggage.getType());
        foundLuggage.setWeight(luggage.getWeight());
        foundLuggage.setColor(luggage.getColor());
        foundLuggage.setPriority(luggage.getPriority());

        foundLuggage.setPerson(person); //cascade will set for Person too
        return this.luggage_repository.save(foundLuggage);
    }

    public void deleteLuggage(Long luggageID) {
        this.luggage_repository.deleteById(luggageID);
    }


    // counting the number of blue luggage per person
    public List<LuggagePersonDTO> getBlueLuggageCount() {
        List<Luggage> blueLuggageList = luggage_repository.findAll(); // not yet blue

//        List<Luggage> blueLuggageList=new ArrayList<>();
//
//        for(Luggage element: LuggageList){
//            if(element.getColor()== "Blue"){
//                blueLuggageList.add(element);
//            }
//
//        }

        Map<Long, Integer> luggageCountMap = new HashMap<>();
        for (Luggage luggage : blueLuggageList) {
            if(luggage.getColor()=="Blue") {
                long personId = luggage.getPerson().getId();
                luggageCountMap.put(personId, luggageCountMap.getOrDefault(personId, 0) + 1);
            }
        }

        List<LuggagePersonDTO> result = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (Map.Entry<Long, Integer> entry : luggageCountMap.entrySet()) {
            Person person = person_repository.findById(entry.getKey()).orElse(null);
            if (person != null) {
                LuggagePersonDTO dto = modelMapper.map(person, LuggagePersonDTO.class);
                dto.setLuggage_id(blueLuggageList.get(0).getId());
                dto.setColor("Blue");
                dto.setNoOfPeople(entry.getValue());
                result.add(dto);
            }
        }

        return result;
    }
}
