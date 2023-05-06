package com.example.busManagement.service;

import com.example.busManagement.domain.Bus_Route;
import com.example.busManagement.domain.DTO.TicketDTO;
import com.example.busManagement.domain.DTO.TicketDTOWithId;
import com.example.busManagement.domain.DTO.TicketDTOwithIdCounter;
import com.example.busManagement.domain.Person;
import com.example.busManagement.domain.Ticket;
import com.example.busManagement.exception.TicketNotFoundException;
import com.example.busManagement.repository.IRepositoryBusRoute;
import com.example.busManagement.repository.IRepositoryPerson;
import com.example.busManagement.repository.IRepositoryTicket;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceTicket {

    private final IRepositoryTicket ticket_repository;
    private final IRepositoryBusRoute busRoute_repository;
    private final IRepositoryPerson person_repository;

    public ServiceTicket(IRepositoryTicket ticket_repository, IRepositoryBusRoute busRoute_repository, IRepositoryPerson person_repository) {
        this.ticket_repository = ticket_repository;
        this.busRoute_repository = busRoute_repository;
        this.person_repository = person_repository;
    }

    public List<TicketDTOWithId> getAllTickets(PageRequest pr) {

        ModelMapper modelMapper= new ModelMapper();
        //Set for each Ticket its own Person's id
        modelMapper.typeMap(Ticket.class, TicketDTOWithId.class)
                .addMapping(ticket ->
                        ticket.getPerson().getId(), TicketDTOWithId::setPersonId)
                .addMapping(ticket ->
                        ticket.getBus_route().getId(), TicketDTOWithId::setBus_routeId);

        return ticket_repository.findAll(pr).stream()
                .map(ticket -> modelMapper.map(ticket, TicketDTOWithId.class))
                .collect(Collectors.toList());


        //Meth2 @@
//        ModelMapper modelMapper = new ModelMapper();
//        //Set for each Ticket its own Person's id
//        modelMapper.typeMap(Ticket.class, TicketDTOwithIdCounter.class)
//                .addMapping(ticket ->
//                        ticket.getPerson().getId(), TicketDTOwithIdCounter::setPersonId)
//                .addMapping(ticket ->
//                        ticket.getBus_route().getId(), TicketDTOwithIdCounter::setBus_routeId);
//
//        List<TicketDTOwithIdCounter> ticketDTOs = new ArrayList<>();
//        for (Ticket ticket : ticket_repository.findAll(pr)) {
//            TicketDTOwithIdCounter dto = modelMapper.map(ticket, TicketDTOwithIdCounter.class);
//            int ticketCount = ticket_repository.countTicketsByBusRouteId(ticket.getBus_route().getId());
//            dto.setNoOfTicketsOfBusRouteId(ticketCount);
//            ticketDTOs.add(dto);
//        }
//
//        return ticketDTOs;

    }

    public TicketDTO getByIdTicket(String id) {
        Long ticketId = Long.parseLong(id);

        Ticket ticket = ticket_repository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(ticket, TicketDTO.class);
    }

    public Ticket addTicket(Ticket newTicket, Long personID, Long busrouteID) {
        Person person=person_repository.findById(personID).get();
        Bus_Route busroute=busRoute_repository.findById(busrouteID).get();
        newTicket.setPerson(person);
        newTicket.setBus_route(busroute);
        newTicket=ticket_repository.save(newTicket);

        person.getTickets().add(newTicket);
        busroute.getTickets().add(newTicket);

        return newTicket;
    }

    public List<Ticket> addSingleTicket(List<TicketDTOWithId> ticketList, Long busrouteID) {
        // Bus route    Ticket   Person

        Bus_Route selectedBusRoute = busRoute_repository.findById(busrouteID).get();
        List<Ticket> finalList= new ArrayList<>();

        for(TicketDTOWithId ticketdto : ticketList){
            Ticket tick=new Ticket();

            // the new ones
            tick.setPayment_method(ticketdto.getPayment_method());
            tick.setSeat_number(ticketdto.getSeat_number());

            tick.setBus_route(selectedBusRoute);


            Long iddddd= Long.valueOf(ticketdto.getPersonId());
            Person selectedPerson=person_repository.findById(iddddd).get();
            tick.setPerson(selectedPerson);

            ticket_repository.save(tick);
            finalList.add(tick);

        }
        return finalList;
    }

    public Ticket updateTicket(Ticket newTicket, Long id) {
        return ticket_repository.findById(id)
                .map(Ticket -> {
                    Ticket.setPayment_method(newTicket.getPayment_method());
                    Ticket.setSeat_number(newTicket.getSeat_number());
                    return ticket_repository.save(Ticket);
                })
                .orElseGet(() -> { // otherwise if not found, ADD IT
                    newTicket.setId(id);
                    return ticket_repository.save(newTicket);
                });
    }

    public void deleteTicket(Long id) {
        ticket_repository.deleteById(id);
    }

    public long getCount() {
        return ticket_repository.count();
    }
}
