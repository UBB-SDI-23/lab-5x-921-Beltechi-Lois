package com.example.busManagement.controller;

import com.example.busManagement.domain.*;
import com.example.busManagement.domain.DTO.BusRouteDTOwithTicketsFK;
import com.example.busManagement.domain.DTO.Bus_Route_PeopleDTO;
import com.example.busManagement.domain.DTO.PersonWithTicketDTO;
import com.example.busManagement.exception.BusRouteNotFoundException;

import com.example.busManagement.repository.IRepositoryBusRoute;

import com.example.busManagement.repository.IRepositoryTicket;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
class ControllerBus_Route {

    private final IRepositoryBusRoute busroute_repository;
    private final IRepositoryTicket ticket_repository;

    ControllerBus_Route(IRepositoryBusRoute busroute_repository, IRepositoryTicket ticket_repository) {
        this.busroute_repository = busroute_repository;
        this.ticket_repository = ticket_repository;
    }


    //GETALL fara tickets ; no People information ok,No_Of_People_Transported
    @GetMapping("/busroutes")
    @CrossOrigin(origins = "*")
    List<Bus_Route_PeopleDTO> all() {

        List<Bus_Route> busroutes = busroute_repository.findAll();
        List<Ticket> tickets = ticket_repository.findAll();

        Map<Long, Integer> busRouteToNumberOfPeopleMap = new HashMap<>();
        for (Ticket ticket : tickets) {
            Long busRouteId = ticket.getBus_route().getId();
            Integer numberOfPeople = busRouteToNumberOfPeopleMap.getOrDefault(busRouteId, 0);
            busRouteToNumberOfPeopleMap.put(busRouteId, numberOfPeople + 1);
        }

        ModelMapper modelMapper = new ModelMapper();
        List<Bus_Route> busRoutes = busroute_repository.findAll();

        return busRoutes.stream()
                .map(busRoute -> {
                    Bus_Route_PeopleDTO dto = modelMapper.map(busRoute, Bus_Route_PeopleDTO.class);
                    Integer numberOfPeople = busRouteToNumberOfPeopleMap.getOrDefault(busRoute.getId(), 0);
                    dto.setNo_Of_People_Transported(numberOfPeople);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    //GET BY ID, fara TICKETS; cu Person & SeatNumber with Date
    @GetMapping("/busroutes/{id}")
    @CrossOrigin(origins = "*")
    BusRouteDTOwithTicketsFK one(@PathVariable String id) {

        Long busrouteId = Long.parseLong(id);

        ModelMapper modelMapper = new ModelMapper();

        Bus_Route busRoute = busroute_repository.findById(busrouteId)
                .orElseThrow(() -> new BusRouteNotFoundException(busrouteId));

        Set<PersonWithTicketDTO> peopleWithTickets = new HashSet<>();
        Set<Ticket> tickets = busRoute.getTickets();

        if (tickets != null) {
            for (Ticket ticket : tickets) {
                PersonWithTicketDTO personWithTicketDTO = new PersonWithTicketDTO();
                Person person = ticket.getPerson();
                personWithTicketDTO.setId(person.getId());
                personWithTicketDTO.setFirstName(person.getFirstName());
                personWithTicketDTO.setLastName(person.getLastName());
                personWithTicketDTO.setDateOfBirth(person.getDateOfBirth());
                personWithTicketDTO.setGender(person.getGender());
                personWithTicketDTO.setPhoneNumber(person.getPhoneNumber());
                personWithTicketDTO.setSeatNumber(ticket.getSeat_number());
                personWithTicketDTO.setDate(ticket.getPurchase_date());
                peopleWithTickets.add(personWithTicketDTO);
            }
        }

        BusRouteDTOwithTicketsFK busRouteDTOwithTicketsFK = modelMapper.map(busRoute, BusRouteDTOwithTicketsFK.class);
        busRouteDTOwithTicketsFK.setPeople(peopleWithTickets);

        return busRouteDTOwithTicketsFK;
    }

    // A2 FILTER
    @GetMapping("/busroutes/higherThanGivenDistance/{value}")
    @CrossOrigin(origins = "*")
    public List<Bus_Route> higherThan(@PathVariable String value) {
        return busroute_repository.findAll()
                .stream()
                .filter(bus_route -> Integer.parseInt(bus_route.getDistance()) > Integer.parseInt(value))
                .collect(Collectors.toList());
    }

    @PostMapping("/busroutes")   // ADD
    Bus_Route newBusRoute(@Valid @RequestBody Bus_Route newBusRoute) {
        return busroute_repository.save(newBusRoute);
    }

    @PutMapping("/busroutes/{id}")   //UPDATE
    Bus_Route replaceBusRoute(@Valid @RequestBody Bus_Route newBus_Route, @PathVariable Long id) {

        return busroute_repository.findById(id)
                .map(Bus_Route -> {
                    Bus_Route.setBus_name(newBus_Route.getBus_name());
                    Bus_Route.setRoute_type(newBus_Route.getRoute_type());
                    Bus_Route.setArrival_hour(newBus_Route.getArrival_hour());
                    Bus_Route.setDeparture_hour(newBus_Route.getDeparture_hour());
                    Bus_Route.setDistance(newBus_Route.getDistance());
                    return busroute_repository.save(Bus_Route);
                })
                .orElseGet(() -> { // otherwise if not found, ADD IT
                    newBus_Route.setId(id);
                    return busroute_repository.save(newBus_Route);
                });
    }

    @DeleteMapping("/busroutes/{id}")   //DELETE
    void deleteBusRoute(@PathVariable Long id) {
        busroute_repository.deleteById(id);
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
