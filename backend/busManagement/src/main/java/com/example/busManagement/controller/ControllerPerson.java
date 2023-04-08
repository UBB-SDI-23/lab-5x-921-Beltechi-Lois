package com.example.busManagement.controller;

import com.example.busManagement.domain.*;
import com.example.busManagement.domain.DTO.PersonAverageDistanceDTO;
import com.example.busManagement.domain.DTO.PersonDTOWith_noBusTaken;
import com.example.busManagement.domain.DTO.PersonDTO_getById_LuggageBusRoutes;
import com.example.busManagement.service.ServicePerson;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    List<PersonDTOWith_noBusTaken> all() {
        return ControllerPerson.getAllPeople();
    }




    @GetMapping("/people/{id}")
    @CrossOrigin(origins = "*")
    public PersonDTO_getById_LuggageBusRoutes one(@PathVariable Long id) {

       return ControllerPerson.getByIdPerson(id);
    }


    // A3 statistic
    @GetMapping("/people/average-distance-of-bus-routes")
    @CrossOrigin(origins = "*")
    public List<PersonAverageDistanceDTO> getPeopleOrderedByAverageDistanceOfBusRoutes() {
        return ControllerPerson.getPeopleOrderedByAverageDistanceOfBusRoutes();
    }



    @PostMapping("/people")
    public Person newPerson(@RequestBody Person newPerson) {
       return ControllerPerson.addPerson(newPerson);
    }


    @PutMapping("/people/{personID}")     //UPDATE
    Person replacePerson(@RequestBody Person person, @PathVariable Long personID)
    {
       return ControllerPerson.updatePerson(person,personID);

    }

    @DeleteMapping("/people/{id}")  //DELETE
    void deletePerson(@PathVariable Long id) {
        ControllerPerson.deletePerson(id);
    }




}
