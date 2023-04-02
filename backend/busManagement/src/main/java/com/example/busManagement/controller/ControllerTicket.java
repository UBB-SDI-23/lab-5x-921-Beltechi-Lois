package com.example.busManagement.controller;

import com.example.busManagement.domain.*;
import com.example.busManagement.domain.DTO.TicketDTO;
import com.example.busManagement.domain.DTO.TicketDTOWithId;
import com.example.busManagement.exception.LuggageNotFoundException;
import com.example.busManagement.repository.IRepositoryBusRoute;
import com.example.busManagement.repository.IRepositoryPerson;
import com.example.busManagement.repository.IRepositoryTicket;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
class ControllerTicket {
    private final IRepositoryTicket ticket_repository;
    private final IRepositoryBusRoute busRoute_repository;
    private final IRepositoryPerson person_repository;

    ControllerTicket(IRepositoryTicket repository, IRepositoryBusRoute busRoute_repository, IRepositoryPerson person_repository) {
        this.ticket_repository = repository;
        this.busRoute_repository = busRoute_repository;
        this.person_repository = person_repository;
    }

    //GETALL without people & busRoutes, only with ID Person,Route[ 1: Person&Route]
    @GetMapping("/tickets")
    @CrossOrigin(origins = "*")
    List<TicketDTOWithId> all() {
        ModelMapper modelMapper= new ModelMapper();
        //Set for each Ticket its own Person's id
        modelMapper.typeMap(Ticket.class, TicketDTOWithId.class)
                .addMapping(ticket ->
                        ticket.getPerson().getId(), TicketDTOWithId::setPersonId)
                .addMapping(ticket ->
                        ticket.getBus_route().getId(), TicketDTOWithId::setBus_routeId);

        return ticket_repository.findAll().stream()
                .map(ticket -> modelMapper.map(ticket, TicketDTOWithId.class))
                .collect(Collectors.toList());
    }

    //GET BY ID, with People && BusRoutes
    @GetMapping("/tickets/{id}")
    @CrossOrigin(origins = "*")
    TicketDTO one(@PathVariable Long id) {

        if (ticket_repository.findById(id).isEmpty())
            throw new LuggageNotFoundException(id);

        //return repository.findById(id).get().toLuggageDTO();

        ModelMapper modelMapper = new ModelMapper();
        TicketDTO ticketDTO = modelMapper.map(ticket_repository.findById(id).get(), TicketDTO.class);
        return ticketDTO;
    }


    // Add to a new ticket existing PersonID & BusRouteID
    @PostMapping("/tickets/people/{personID}/busroutes/{busrouteID}")   // ADD
    Ticket newTicket(@RequestBody Ticket newTicket,@PathVariable Long personID,@PathVariable Long busrouteID)
    {
        Person person=person_repository.findById(personID).get();
        Bus_Route busroute=busRoute_repository.findById(busrouteID).get();
        newTicket.setPerson(person);
        newTicket.setBus_route(busroute);
        newTicket=ticket_repository.save(newTicket);

        person.getTickets().add(newTicket);
        busroute.getTickets().add(newTicket);

        return newTicket;
    }


    // A4 requirement; Add to an existing [list of tickets] an existing busRoute
    @PostMapping("/tickets/{busrouteID}")   // ADD
    List<Ticket> newTicketList(@RequestBody List<TicketDTOWithId> TicketList, @PathVariable Long busrouteID)
    {
        // Bus route    Ticket   Person
        // !!
        Bus_Route selectedBusRoute = busRoute_repository.findById(busrouteID).get();
        List<Ticket> finalList= new ArrayList<>();

        for(TicketDTOWithId ticketdto : TicketList){
            Ticket tick=new Ticket();

            // alea noi
            tick.setPurchase_date(ticketdto.getPurchase_date());
            tick.setSeat_number(ticketdto.getSeat_number());

//            int busRouteID = ticketdto.getBus_routeId();
//            Long idd = Long.valueOf(busRouteID);
//            Bus_Route bus=busRoute_repository.findById(idd).get();
//            tick.setBus_route(bus);

            tick.setBus_route(selectedBusRoute);


            Long iddddd= Long.valueOf(ticketdto.getPersonId());
            Person selectedPerson=person_repository.findById(iddddd).get();
            tick.setPerson(selectedPerson);

//            int PersonID=ticketdto.getPersonId();
//            Long iddd=Long.valueOf(PersonID);;
//            Person person = person_repository.findById(iddd).get();
//            tick.setPerson(person);

            ticket_repository.save(tick);
            finalList.add(tick);

        }
        return finalList;
    }


    @PutMapping("/tickets/{id}")     //UPDATE
    Ticket replaceTicket(@RequestBody Ticket newTicket, @PathVariable Long id) {

        return ticket_repository.findById(id)
                .map(Ticket -> {
                    Ticket.setPurchase_date(newTicket.getPurchase_date());
                    Ticket.setSeat_number(newTicket.getSeat_number());
                    return ticket_repository.save(Ticket);
                })
                .orElseGet(() -> { // otherwise if not found, ADD IT
                    newTicket.setId(id);
                    return ticket_repository.save(newTicket);
                });
    }


    @DeleteMapping("/tickets/{id}")  //DELETE
    void deleteTicket(@PathVariable Long id) {
        ticket_repository.deleteById(id);
    }


}
