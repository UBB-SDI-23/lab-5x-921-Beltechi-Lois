package com.example.busManagement.service;

import com.example.busManagement.domain.Bus_Route;
import com.example.busManagement.domain.DTO.*;
import com.example.busManagement.domain.Person;
import com.example.busManagement.domain.Ticket;
import com.example.busManagement.exception.PersonNotFoundException;
import com.example.busManagement.repository.IRepositoryPerson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServicePerson {
    private final IRepositoryPerson person_repository;

    public ServicePerson(IRepositoryPerson person_repository) {
        this.person_repository = person_repository;
    }


    public List<PersonDTOWith_noBusTaken> getAllPeople() {
        List<Person> people = person_repository.findAll();
        Map<Long, Integer> personToBusRoutesMap = new HashMap<>();

        for (Person person : people) {
            int count = 0;
            for (Ticket ticket : person.getTickets()) {
                if(person.getId()==ticket.getPerson().getId()){  // Current Person == appears in current ticket
                    count++;
                }
            }
            personToBusRoutesMap.put(person.getId(), count);
        }

        ModelMapper modelMapper = new ModelMapper();

        return people.stream()
                .map(person -> {
                    PersonDTOWith_noBusTaken dto = modelMapper.map(person, PersonDTOWith_noBusTaken.class);
                    dto.setNumBusRoutesTaken(personToBusRoutesMap.get(person.getId()));
                    return dto;
                })
                .collect(Collectors.toList());
    }


    // Luggage + Tickets[NO  of busroutes]
    public PersonDTO_getById_LuggageBusRoutes getByIdPerson(Long id) {
        Optional<Person> personOptional = person_repository.findById(id);

        if (personOptional.isEmpty()) {
            throw new PersonNotFoundException(id);
        }

        Person person = personOptional.get();


        List<Bus_Route_TicketDTO> busRoutes = new ArrayList<>();
        List<Ticket> tickets = person.getTickets();

        if (tickets != null) {
            for (Ticket ticket : tickets) {
                if (ticket.getPerson().getId() == id) {
                    Bus_Route_TicketDTO busRoute = new Bus_Route_TicketDTO();
                    busRoute.setId(ticket.getBus_route().getId());
                    busRoute.setBus_name(ticket.getBus_route().getBus_name());
                    busRoute.setRoute_type(ticket.getBus_route().getRoute_type());
                    busRoute.setDeparture_hour(ticket.getBus_route().getDeparture_hour());
                    busRoute.setArrival_hour(ticket.getBus_route().getArrival_hour());
                    busRoute.setDistance(ticket.getBus_route().getDistance());
                    busRoute.setSeatNumber(ticket.getSeat_number());
                    busRoute.setPurchase_method(ticket.getPayment_method());
                    busRoutes.add(busRoute);
                }
            }
        }

        ModelMapper modelMapper = new ModelMapper();

        // Retrieve the luggage information for the person and map it to a list of LuggageNoPersonDTO objects
        List<LuggageNoPersonDTO> luggageList = person.getLuggage().stream()
                .map(luggage -> modelMapper.map(luggage, LuggageNoPersonDTO.class))
                .collect(Collectors.toList());

        modelMapper.typeMap(Person.class, PersonDTO_getById_LuggageBusRoutes.class)
                .addMappings(mapper -> {
                    mapper.skip(PersonDTO_getById_LuggageBusRoutes::setBusroutes);
                    mapper.skip(PersonDTO_getById_LuggageBusRoutes::setLuggages);
                });

        PersonDTO_getById_LuggageBusRoutes personDTOid = modelMapper.map(person, PersonDTO_getById_LuggageBusRoutes.class);
        personDTOid.setBusroutes(busRoutes);
        personDTOid.setLuggages(luggageList);
        return personDTOid;
    }

    public Person addPerson(Person newPerson) {
        return this.person_repository.save(newPerson);
    }

    public Person updatePerson(Person person, Long personID) {

        Person foundPerson = this.person_repository.findById(personID).get();
        foundPerson.setFirstName(person.getFirstName());
        foundPerson.setLastName(person.getLastName());
        foundPerson.setNationality(person.getNationality());
        foundPerson.setGender(person.getGender());
        foundPerson.setPhoneNumber(person.getPhoneNumber());
        return this.person_repository.save(foundPerson);
    }

    public void deletePerson(Long id) {

        person_repository.deleteById(id);
    }

    public List<PersonAverageDistanceDTO> getPeopleOrderedByAverageDistanceOfBusRoutes() {
        List<PersonAverageDistanceDTO> result = new ArrayList<>();

        List<Person> people = person_repository.findAll();

        for (Person person : people) {
            double totalDistance = 0;
            int routeCount = 0;

            // Find for current Person all its tickets, along with their Total_Distance of BusRoute
            for (Ticket ticket : person.getTickets()) {
                Bus_Route route = ticket.getBus_route();  //Get FK BusRoute from Ticket
                if (route != null) {
                    String distanceStr = route.getDistance();
                    if (distanceStr != null) {
                        try {
                            double distance = Double.parseDouble(distanceStr);
                            totalDistance += distance;
                            routeCount++;
                        } catch (NumberFormatException e) {
                            // ignore invalid distance values
                        }
                    }
                }
            }

            if (routeCount > 0) {
                double averageDistance = totalDistance / routeCount;
                PersonAverageDistanceDTO dto = new PersonAverageDistanceDTO(
                        person.getId(), person.getFirstName(), person.getLastName(), averageDistance);
                result.add(dto);
            }
        }

        result.sort(Comparator.comparingDouble(PersonAverageDistanceDTO::getAverageDistance)
                .thenComparingLong(PersonAverageDistanceDTO::getPersonId));

        return result;
    }
}
