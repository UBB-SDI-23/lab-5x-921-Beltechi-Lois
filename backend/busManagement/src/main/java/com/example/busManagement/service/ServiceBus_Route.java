package com.example.busManagement.service;

import com.example.busManagement.domain.Bus_Route;
import com.example.busManagement.domain.DTO.BusRouteDTOwithTicketsFK;
import com.example.busManagement.domain.DTO.BusRoutesAveragePeopleDTO;
import com.example.busManagement.domain.DTO.Bus_Route_PeopleDTO;
import com.example.busManagement.domain.DTO.PersonWithTicketDTO;
import com.example.busManagement.domain.Person;
import com.example.busManagement.domain.Ticket;
import com.example.busManagement.exception.BusRouteNotFoundException;
import com.example.busManagement.repository.IRepositoryBusRoute;
import com.example.busManagement.repository.IRepositoryTicket;
import org.modelmapper.ModelMapper;
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


    public List<Bus_Route_PeopleDTO> getAllbusroutes() {
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

    public BusRouteDTOwithTicketsFK getByIdbusroutes(String id) {
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

    public List<Bus_Route> filterHigherThanGivenDistance(String value) {

        return busroute_repository.findAll()
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

    public List<BusRoutesAveragePeopleDTO> getBusRoutesOrderedByPeopleTransported() {
        List<BusRoutesAveragePeopleDTO> result = new ArrayList<>();

        List<Bus_Route> busRoutes = busroute_repository.findAll();

        for (Bus_Route busRoute : busRoutes) {
            int totalPeopleTransported = 0;

            // Find for current Bus_Route all its Tickets, along with their number of People_Transported
            for (Ticket ticket : busRoute.getTickets()) {
                if (ticket.getPerson() != null) {
                    totalPeopleTransported++;
                }
            }

            BusRoutesAveragePeopleDTO dto = new BusRoutesAveragePeopleDTO(
                    busRoute.getId(), busRoute.getBus_name(), busRoute.getRoute_type(), totalPeopleTransported);
            result.add(dto);
        }

        result.sort(Comparator.comparingInt(BusRoutesAveragePeopleDTO::getNoOfPeopleTransported)
                .reversed());

        return result;
    }
}
