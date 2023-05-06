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

    public List<Bus_RouteAllTicketsDTO> getAllbusroutes(PageRequest pr) {
        // Initial, fara aggregated value
//        Page<Bus_Route> busRoutePage = busroute_repository.findAll(pr);
//        ModelMapper modelMapper = new ModelMapper();
//        return busRoutePage.stream()
//                .map(busRoute -> modelMapper.map(busRoute, Bus_RouteDTO.class))
//                .collect(Collectors.toList());

        //Method 1 Aggregate
//        Page<Bus_Route> busRoutePage = busroute_repository.findAll(pr);
//        ModelMapper modelMapper = new ModelMapper();
//        return busRoutePage.stream()
//                .map(busRoute -> {
//                    Bus_RouteAllTicketsDTO dto = modelMapper.map(busRoute, Bus_RouteAllTicketsDTO.class);
//                    int count = busroute_repository.countTicketsByBusRouteId(busRoute.getId()); // add this line
////                    dto.setNoOfTicketsOfBusRouteId(busRoute.getTickets().size());
//                    dto.setNoOfTicketsOfBusRouteId(count);
//
//
//                    return dto;
//                })
//                .collect(Collectors.toList());



        // Method1

        //Sort sort = Sort.by("tickets.id").ascending();
//        Page<Bus_Route> busRoutePage = busroute_repository.findAll(pr);
//        ModelMapper modelMapper = new ModelMapper();
//        return busRoutePage.stream()
//                .map(busRoute -> {
//                    Bus_RouteAllTicketsDTO dto = modelMapper.map(busRoute, Bus_RouteAllTicketsDTO.class);
//                    //dto.setNoOfTicketsOfBusRouteId(busRoute.getTickets().size());
//                    dto.setNoOfTicketsOfBusRouteId(busroute_repository.countTicketsByBusRouteId(busRoute.getId()));
//                    return dto;
//                })
//                .collect(Collectors.toList());

        // Method 1.2
        ModelMapper modelMapper = new ModelMapper();
        List<Bus_RouteAllTicketsDTO> busrouteDTOs = new ArrayList<>();
        for (Bus_Route busRoute : busroute_repository.findAll(pr)) {
            Bus_RouteAllTicketsDTO dto = modelMapper.map(busRoute, Bus_RouteAllTicketsDTO.class);
            //int ticketCount = ticket_repository.countTicketsByBusRouteId(busroute.getBus_route().getId());
            //dto.setNoOfTicketsOfBusRouteId(ticketCount);
            dto.setNoOfTicketsOfBusRouteId(busroute_repository.countTicketsByBusRouteId(busRoute.getId()));
            busrouteDTOs.add(dto);
        }

        return busrouteDTOs;

        //Method2
//        List<Object[]> results = busroute_repository.countTicketsByBusRouteIdUsingJoin(pr.getOffset(), pr.getPageSize());
//
//        List<Bus_RouteAllTicketsDTO> dtos = new ArrayList<>();
//        for (Object[] result : results) {
//            Bus_Route busRoute = (Bus_Route) result[0];
//            int count = (int) result[1];
//
//            Bus_RouteAllTicketsDTO dto = new Bus_RouteAllTicketsDTO();
//            dto.setId(busRoute.getId());
//            dto.setDeparture_hour(busRoute.getDeparture_hour());
//            dto.setArrival_hour(busRoute.getArrival_hour());
//            dto.setBus_name(busRoute.getBus_name());
//            dto.setDistance(busRoute.getDistance());
//            dto.setRoute_type(busRoute.getRoute_type());
//            dto.setNoOfTicketsOfBusRouteId(count);
//
//            dtos.add(dto);
//        }
//
//        return dtos;



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

        Bus_Route busRoute = busroute_repository.findById(busrouteId)  //**
                .orElseThrow(() -> new BusRouteNotFoundException(busrouteId));

        List<PersonWithTicketDTO> peopleWithTickets = new ArrayList<>();
        //List<Ticket> tickets = busRoute.getTickets(); //**
        // Fetch tickets for the given bus route id using a custom query
        List<Ticket> tickets = ticket_repository.findByBusRouteId(busrouteId);

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

//        return busroute_repository.findAll(pr)
//                .stream()
//                .filter(bus_route -> Integer.parseInt(bus_route.getDistance()) > Integer.parseInt(value))
//                .collect(Collectors.toList());

        //List<Bus_Route> allBusRoutes = busroute_repository.findAll(pr).getContent(); // A list

        //PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);


        return busroute_repository.findByDistanceGreaterThan(Integer.parseInt(value), pr);

//        // Filter the original list of bus routes based on the filtered list
//        return allBusRoutes.stream()
//                .filter(filteredBusRoutes::contains)
//                .collect(Collectors.toList());

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

    public List<Bus_Route> getBusRoutesIdsAutocomplete(String query) {
        PageRequest pr = PageRequest.of(0,500);

        Page<Bus_Route> busroutes = busroute_repository.findAll(pr);

        return busroutes.stream()
//                .filter(person -> String.valueOf(person.getId()).startsWith(query))
                .filter(busroute -> busroute.getBus_name().toLowerCase().contains(query.toLowerCase())).limit(20)
                .limit(20)
                .collect(Collectors.toList());
    }


//    public long countByDistanceGreaterThan(int distance) {
//            return busroute_repository.count(
//                    (root, query, builder) -> builder.greaterThan(root.get("distance"), distance)
//
//    }
}
