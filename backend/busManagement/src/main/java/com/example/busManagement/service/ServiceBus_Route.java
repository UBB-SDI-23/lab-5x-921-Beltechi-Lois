package com.example.busManagement.service;

import com.example.busManagement.domain.Bus_Route;
import com.example.busManagement.domain.DTO.*;
import com.example.busManagement.domain.Person;
import com.example.busManagement.domain.Ticket;
import com.example.busManagement.exception.BusRouteNotFoundException;
import com.example.busManagement.repository.IRepositoryBusRoute;
import com.example.busManagement.repository.IRepositoryTicket;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceBus_Route {

    private final IRepositoryBusRoute busroute_repository;
    private final IRepositoryTicket ticket_repository;

    public ServiceBus_Route(IRepositoryBusRoute busroute_repository, IRepositoryTicket ticket_repository) {
        this.busroute_repository = busroute_repository;
        this.ticket_repository = ticket_repository;
    }

    public List<Bus_RouteDTO> getAllbusroutes(PageRequest pr) {
        //Pageable pageable = PageRequest.of(0, 50);
        //Page<Bus_Route> busRoutePage = busroute_repository.findAll(pageable);
        Page<Bus_Route> busRoutePage = busroute_repository.findAll(pr);
        ModelMapper modelMapper = new ModelMapper();
        return busRoutePage.stream()
                .map(busRoute -> modelMapper.map(busRoute, Bus_RouteDTO.class))
                .collect(Collectors.toList());
    }


//    public List<Bus_Route_PeopleDTO> getAllbusroutes() {
//        //List<Bus_Route> busroutes = busroute_repository.findAll();
//        List<Ticket> tickets = ticket_repository.findAll();
//
//        //addd
//        Map<Long, Integer> busRouteToNumberOfPeopleMap = new HashMap<>();
//        for (Ticket ticket : tickets) {
//            Long busRouteId = ticket.getBus_route().getId();
//            Integer numberOfPeople = busRouteToNumberOfPeopleMap.getOrDefault(busRouteId, 0);
//            busRouteToNumberOfPeopleMap.put(busRouteId, numberOfPeople + 1);
//        }
//
//        ModelMapper modelMapper = new ModelMapper();
//        List<Bus_Route> busRoutes = busroute_repository.findAll();
//
//        return busRoutes.stream()
//                .map(busRoute -> {
//                    Bus_Route_PeopleDTO dto = modelMapper.map(busRoute, Bus_Route_PeopleDTO.class);
//                    Integer numberOfPeople = busRouteToNumberOfPeopleMap.getOrDefault(busRoute.getId(), 0);
//                    dto.setNo_Of_People_Transported(numberOfPeople);
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }

//    public List<Bus_Route_PeopleDTO> getAllbusroutes() {
//        int page = 0;
//        int size = 50;
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Ticket> ticketPage;
//        List<Ticket> tickets = new ArrayList<>();
//        Map<Long, Integer> busRouteToNumberOfPeopleMap = new HashMap<>();
//        do {
//            ticketPage = ticket_repository.findAll(pageable);
//            tickets.addAll(ticketPage.getContent());
//            for (Ticket ticket : ticketPage.getContent()) {
//                Long busRouteId = ticket.getBus_route().getId();
//                Integer numberOfPeople = busRouteToNumberOfPeopleMap.getOrDefault(busRouteId, 0);
//                busRouteToNumberOfPeopleMap.put(busRouteId, numberOfPeople + 1);
//            }
//            pageable = ticketPage.nextPageable();
//        } while (ticketPage.hasNext());
//
//        ModelMapper modelMapper = new ModelMapper();
//        Page<Bus_Route> busRoutePage;
//        List<Bus_Route_PeopleDTO> busRouteDTOs = new ArrayList<>();
//        Set<Long> countedBusRouteIds = new HashSet<>();
//        do {
//            busRoutePage = busroute_repository.findAll(pageable);
//            List<Bus_Route> busRoutes = busRoutePage.getContent();
//            for (Bus_Route busRoute : busRoutes) {
//                if (countedBusRouteIds.contains(busRoute.getId())) {
//                    continue; // Skip bus routes that have already been counted
//                }
//                Integer numberOfPeople = busRouteToNumberOfPeopleMap.getOrDefault(busRoute.getId(), 0);
//                Bus_Route_PeopleDTO dto = modelMapper.map(busRoute, Bus_Route_PeopleDTO.class);
//                dto.setNo_Of_People_Transported(numberOfPeople);
//                busRouteDTOs.add(dto);
//                countedBusRouteIds.add(busRoute.getId());
//            }
//            pageable = busRoutePage.nextPageable();
//        } while (busRoutePage.hasNext());
//
//        return busRouteDTOs;
//    }


    public BusRouteDTOwithTicketsFK getByIdbusroutes(String id) {
        Long busrouteId = Long.parseLong(id);

        ModelMapper modelMapper = new ModelMapper();

        Bus_Route busRoute = busroute_repository.findById(busrouteId)
                .orElseThrow(() -> new BusRouteNotFoundException(busrouteId));

        List<PersonWithTicketDTO> peopleWithTickets = new ArrayList<>();
        List<Ticket> tickets = busRoute.getTickets();

        if (tickets != null) {
            for (Ticket ticket : tickets) {
                PersonWithTicketDTO personWithTicketDTO = new PersonWithTicketDTO();
                Person person = ticket.getPerson();
                personWithTicketDTO.setId(person.getId());
                personWithTicketDTO.setFirstName(person.getFirstName());
                personWithTicketDTO.setLastName(person.getLastName());
                personWithTicketDTO.setNationality(person.getNationality());
                personWithTicketDTO.setGender(person.getGender());
                personWithTicketDTO.setPhoneNumber(person.getPhoneNumber());
                personWithTicketDTO.setSeatNumber(ticket.getSeat_number());
                personWithTicketDTO.setPayment_method(ticket.getPayment_method());
                peopleWithTickets.add(personWithTicketDTO);
            }
        }

        BusRouteDTOwithTicketsFK busRouteDTOwithTicketsFK = modelMapper.map(busRoute, BusRouteDTOwithTicketsFK.class);
        busRouteDTOwithTicketsFK.setPeople(peopleWithTickets);

        return busRouteDTOwithTicketsFK;
    }

    public List<Bus_Route> filterHigherThanGivenDistance(String value, PageRequest pr) {
        //Pageable pageable = PageRequest.of(0, 50);
//        return busroute_repository.findAll(pageable)
//                .stream()
//                .filter(bus_route -> Integer.parseInt(bus_route.getDistance()) > Integer.parseInt(value))
//                .collect(Collectors.toList());

        return busroute_repository.findAll(pr)
                .stream()
                .filter(bus_route -> Integer.parseInt(bus_route.getDistance()) > Integer.parseInt(value))
                .collect(Collectors.toList());
    }

    public Bus_Route addBusRoute(Bus_Route newBusRoute) {
        return busroute_repository.save(newBusRoute);
    }

    public Bus_Route updateBusRoute(Bus_Route newBus_route, Long id) {
        return busroute_repository.findById(id)
                .map(Bus_Route -> {
                    Bus_Route.setBus_name(newBus_route.getBus_name());
                    Bus_Route.setRoute_type(newBus_route.getRoute_type());
                    Bus_Route.setArrival_hour(newBus_route.getArrival_hour());
                    Bus_Route.setDeparture_hour(newBus_route.getDeparture_hour());
                    Bus_Route.setDistance(newBus_route.getDistance());
                    return busroute_repository.save(Bus_Route);
                })
                .orElseGet(() -> { // otherwise if not found, ADD IT
                    newBus_route.setId(id);
                    return busroute_repository.save(newBus_route);
                });
    }

    public void deleteBusRoute(Long id) {
        busroute_repository.deleteById(id);
    }


    // INITIAL

    public long getCount() {
        return busroute_repository.count();

    }




}
