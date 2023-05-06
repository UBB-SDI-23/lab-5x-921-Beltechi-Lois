package com.example.busManagement.controller;

import com.example.busManagement.domain.*;
import com.example.busManagement.domain.DTO.*;
import com.example.busManagement.service.ServicePerson;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
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
    @GetMapping("/api/people/page/{page}/size/{size}")
    @CrossOrigin(origins = "*")
    List<PersonAllLuggagesDTO> all(@PathVariable int page, @PathVariable int size) {

        PageRequest pr = PageRequest.of(page,size);
        return ControllerPerson.getAllPeople(pr);
    }




    @GetMapping("/api/people/{id}")
    @CrossOrigin(origins = "*")
    public PersonDTO_getById_Luggage one(@PathVariable String id) {

       return ControllerPerson.getByIdPerson(id);
    }


    // A3 statistic
    @GetMapping("/api/people/average-distance-of-luggage-weight/page/{page}/size/{size}")
    @CrossOrigin(origins = "*")
    public List<PersonAverageLugWeightDTO> getPeopleOrderedByAverageWeightOfLuggages(@PathVariable int page, @PathVariable int size) {
        PageRequest pr = PageRequest.of(page,size);
        return ControllerPerson.getPeopleOrderedByAverageWeightOfLuggages(pr);
    }


    @GetMapping("/api/people/count")
    @CrossOrigin(origins = "*")
    long count() {
        return ControllerPerson.getCount();
    }

    @GetMapping("/api/people/autocomplete")
    public List<Person> getPeopleSuggestions(@RequestParam String query)
    {
        return this.ControllerPerson.getPeopleIdsAutocomplete(query);
    }

    @PostMapping("/api/people")
    public Person newPerson(@Valid @RequestBody Person newPerson) {
       return ControllerPerson.addPerson(newPerson);
    }

    @PutMapping("/api/people/{personID}")     //UPDATE
    Person replacePerson(@Valid @RequestBody Person person, @PathVariable Long personID)
    {
       return ControllerPerson.updatePerson(person,personID);

    }

    @DeleteMapping("/api/people/{id}")  //DELETE
    void deletePerson(@PathVariable Long id) {
        ControllerPerson.deletePerson(id);
    }




}
