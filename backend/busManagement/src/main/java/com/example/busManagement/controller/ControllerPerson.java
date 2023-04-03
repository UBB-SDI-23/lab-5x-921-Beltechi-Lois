package com.example.busManagement.controller;

import com.example.busManagement.domain.*;
import com.example.busManagement.domain.DTO.Bus_Route_TicketDTO;
import com.example.busManagement.domain.DTO.PersonAverageDistanceDTO;
import com.example.busManagement.domain.DTO.PersonDTOWithId_1_1;
import com.example.busManagement.domain.DTO.PersonDTO_getId_1_m_Pass_Lug;
import com.example.busManagement.exception.PersonNotFoundException;
import com.example.busManagement.repository.IRepositoryPassenger;
import com.example.busManagement.repository.IRepositoryPerson;
import com.example.busManagement.repository.IRepositoryTicket;
import com.example.busManagement.service.ServicePerson;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public
class ControllerPerson {

    private final ServicePerson ControllerPerson;



    ControllerPerson(ServicePerson controllerPerson) {
        ControllerPerson = controllerPerson;
    }

    //GETALL without Tickets, with ID Passenger 1:1, No_Of_BusRoutesTaken for each Person
    @GetMapping("/people")
    @CrossOrigin(origins = "*")
    List<PersonDTOWithId_1_1> all() {
        return ControllerPerson.getAllPeople();
    }

    @GetMapping("/people/{id}")
    @CrossOrigin(origins = "*")
    public PersonDTO_getId_1_m_Pass_Lug one(@PathVariable Long id) {

       return ControllerPerson.getByIdPerson(id);
    }


    //  ADD a Person with an existing Passenger ;
    //  or ADD an existing Passenger to the Current Person
    @PostMapping("/people/passenger/{passengerID}")
    Person newPerson(@RequestBody Person newPerson, @PathVariable Long passengerID) {
       return ControllerPerson.addPerson(newPerson,passengerID);
    }


    @PutMapping("/people/{personID}/passengers/{passengerID}")     //UPDATE
    Person replacePerson(@RequestBody Person person, @PathVariable Long personID, @PathVariable Long passengerID)
    {
       return ControllerPerson.updatePerson(person,personID,passengerID);

    }

    @DeleteMapping("/people/{id}")  //DELETE
    void deletePerson(@PathVariable Long id) {
        ControllerPerson.deletePerson(id);
    }


    // A3 statistic
    @GetMapping("/people/average-distance-of-bus-routes")
    @CrossOrigin(origins = "*")
    public List<PersonAverageDistanceDTO> getPeopleOrderedByAverageDistanceOfBusRoutes() {
        return ControllerPerson.getPeopleOrderedByAverageDistanceOfBusRoutes();
    }


}
