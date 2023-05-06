package com.example.busManagement.service;

import com.example.busManagement.domain.Bus_Route;
import com.example.busManagement.domain.DTO.LuggageDTO;
import com.example.busManagement.domain.DTO.LuggageDTOWithId;
import com.example.busManagement.domain.DTO.LuggagePersonDTO;
import com.example.busManagement.domain.DTO.LuggagePersonNationalityDTO;
import com.example.busManagement.domain.Luggage;
import com.example.busManagement.domain.Person;
import com.example.busManagement.exception.LuggageNotFoundException;
import com.example.busManagement.repository.IRepositoryLuggage;
import com.example.busManagement.repository.IRepositoryPerson;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public List<LuggageDTOWithId> getAllLuggages(PageRequest pr) {

        //Page<Bus_Route> busRoutePage = busroute_repository.findAll(pr);

        ModelMapper modelMapper= new ModelMapper();

        //Set for each Luggage its own Passenger's id
        modelMapper.typeMap(Luggage.class, LuggageDTOWithId.class)
                .addMapping(luggage ->
                        luggage.getPerson().getId(), LuggageDTOWithId::setPerson_Id);

        PageRequest selectedPage = PageRequest.of(pr.getPageNumber(), pr.getPageSize());

        return luggage_repository.findAll(selectedPage).stream()
                .map(luggage -> modelMapper.map(luggage, LuggageDTOWithId.class))
                .collect(Collectors.toList());
    }

    public LuggageDTO getByIdLuggage(String id) {

        Long luggageId = Long.parseLong(id);

//        if (luggage_repository.findById(id).isEmpty())
//            throw new LuggageNotFoundException(id);
//
//        ModelMapper modelMapper = new ModelMapper();
//        LuggageDTO luggageDTO = modelMapper.map(luggage_repository.findById(id).get(), LuggageDTO.class);
//        return luggageDTO;


        Luggage luggage = luggage_repository.findById(luggageId)
                .orElseThrow(() -> new LuggageNotFoundException(luggageId));

        return new ModelMapper().map(luggage, LuggageDTO.class);
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
        foundLuggage.setDescription(luggage.getDescription());

        foundLuggage.setPerson(person); //cascade will set for Person too ; I dont think so
        return this.luggage_repository.save(foundLuggage);
    }

    public void deleteLuggage(Long luggageID) {
        this.luggage_repository.deleteById(luggageID);
    }


    public List<LuggagePersonNationalityDTO> getAmericanLuggages(PageRequest pr) {

        List<LuggagePersonNationalityDTO> americanLuggages = new ArrayList<>();

        // Get all people and their luggages
        Page<Person> people = person_repository.findAll(pr);
        for (Person person : people) {
            if (person.getNationality().equals("American")) {
//                List<Luggage> luggages = person.getLuggage();  //Query **
                List<Luggage> luggages = luggage_repository.findLuggagesByPersonId(person.getId());
                for (Luggage luggage : luggages) {
                    LuggagePersonNationalityDTO dto = new LuggagePersonNationalityDTO();
                    dto.setLuggage_id(luggage.getId());
                    dto.setWeight(luggage.getWeight());
                    dto.setPriority(luggage.getPriority());
                    dto.setStatus(luggage.getStatus());
                    dto.setPerson_nationality(person.getNationality());
                    americanLuggages.add(dto);
                }
            }
        }

        return americanLuggages;
    }

    public long getCount() {
        return luggage_repository.count();
    }
}
