package com.example.busManagement.controller;

import com.example.busManagement.domain.*;
import com.example.busManagement.domain.DTO.TicketDTO;
import com.example.busManagement.domain.DTO.TicketDTOWithId;
import com.example.busManagement.domain.DTO.TicketDTOwithIdCounter;
import com.example.busManagement.service.ServiceTicket;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
class ControllerTicket {

    private final ServiceTicket ControllerTicket;

    ControllerTicket(ServiceTicket controllerTicket) {
        ControllerTicket = controllerTicket;
    }

    //GETALL without people & busRoutes, only with ID Person,Route[ 1: Person&Route]
    @GetMapping("/api/tickets/page/{page}/size/{size}")
    @CrossOrigin(origins = "*")
    List<TicketDTOWithId> all(@PathVariable int page, @PathVariable int size) {
        PageRequest pr = PageRequest.of(page,size);
        return ControllerTicket.getAllTickets(pr);
    }

    //GET BY ID, with People && BusRoutes
    @GetMapping("/api/tickets/{id}")
    @CrossOrigin(origins = "*")
    TicketDTO one(@PathVariable String id) {

        return ControllerTicket.getByIdTicket(id);
    }

    @GetMapping("/api/tickets/count")
    @CrossOrigin(origins = "*")
    long count() {
        return ControllerTicket.getCount();
    }


    // Add to a new ticket existing PersonID & BusRouteID
    @PostMapping("/api/tickets/people/{personID}/busroutes/{busrouteID}")   // ADD
    Ticket newTicket(@Valid @RequestBody Ticket newTicket,@PathVariable Long personID,@PathVariable Long busrouteID)
    {
       return ControllerTicket.addTicket(newTicket,personID,busrouteID);
    }


    // A4 requirement; Add to an existing [list of tickets] an existing busRoute
    @PostMapping("/api/tickets/{busrouteID}")   // ADD
    List<Ticket> newTicketList(@Valid @RequestBody List<TicketDTOWithId> TicketList, @PathVariable Long busrouteID)
    {
        return ControllerTicket.addSingleTicket(TicketList,busrouteID);
    }


    @PutMapping("/api/tickets/{id}")     //UPDATE
    Ticket replaceTicket(@Valid @RequestBody Ticket newTicket, @PathVariable Long id) {

       return ControllerTicket.updateTicket(newTicket,id);
    }


    @DeleteMapping("/api/tickets/{id}")  //DELETE
    void deleteTicket(@PathVariable Long id) {

        ControllerTicket.deleteTicket(id);
    }


}
