package com.example.busManagement.controller;

import com.example.busManagement.domain.*;
import com.example.busManagement.exception.PersonNotFoundException;
import com.example.busManagement.repository.IRepositoryPassenger;
import com.example.busManagement.repository.IRepositoryPerson;
import com.example.busManagement.repository.IRepositoryTicket;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public
//@CrossOrigin(origins = "*") //to allow requests from any domain
//@CrossOrigin(origins = "http://localhost:8080")
class ControllerPerson {

    private final IRepositoryPerson person_repository;
    private final IRepositoryTicket ticket_repository;

    private final IRepositoryPassenger passenger_repository;


    ControllerPerson(IRepositoryPerson repository, IRepositoryTicket ticket_repository, IRepositoryPassenger passenger_repository) {
        this.person_repository = repository;
        this.ticket_repository = ticket_repository;
        this.passenger_repository = passenger_repository;
    }

    @GetMapping("/people")  //GETALL Fara Tickets, cu ID Passenger 1:1, No_Of_BusRoutesTaken for each Person
    @CrossOrigin(origins = "*")
    List<PersonDTOWithId_1_1> all() {
        List<Person> people = person_repository.findAll();
        Map<Long, Integer> personToBusRoutesMap = new HashMap<>();

        for (Person person : people) {
            int count = 0;
            for (Ticket ticket : person.getTickets()) {
                if(person.getId()==ticket.getPerson().getId()){ // Current Person == appears in current ticket?
                    count++;
                }
            }
            personToBusRoutesMap.put(person.getId(), count);
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Person.class, PersonDTOWithId_1_1.class)
                .addMapping(person -> person.getPassenger().getId(), PersonDTOWithId_1_1::setPassengerId);

        return people.stream()
                .map(person -> {
                    PersonDTOWithId_1_1 dto = modelMapper.map(person, PersonDTOWithId_1_1.class);
                    dto.setNumBusRoutesTaken(personToBusRoutesMap.get(person.getId()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/people/{id}")
    @CrossOrigin(origins = "*")
    public PersonDTO_getId_1_m_Pass_Lug one(@PathVariable Long id) {

        Optional<Person> personOptional = person_repository.findById(id);

        if (personOptional.isEmpty()) {
            throw new PersonNotFoundException(id);
        }

        Person person = personOptional.get();

        Set<Bus_Route_TicketDTO> busRoutes = new HashSet<>();
        Set<Ticket> tickets = person.getTickets();

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
                    busRoute.setDate(ticket.getPurchase_date());
                    busRoutes.add(busRoute);
                }
            }
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Person.class, PersonDTO_getId_1_m_Pass_Lug.class)
                .addMappings(mapper -> mapper.skip(PersonDTO_getId_1_m_Pass_Lug::setBusroutes));
        // other mappings...

        PersonDTO_getId_1_m_Pass_Lug personDTOid = modelMapper.map(person, PersonDTO_getId_1_m_Pass_Lug.class);
        personDTOid.setBusroutes(busRoutes);
        return personDTOid;
    }


    //  /passengers/{passengerID}/people
    //  ADD a Person with an existing Passenger ; or ADD an existing Passenger to the Current Person
    @PostMapping("/people/passenger/{passengerID}")
    Person newPerson(@RequestBody Person newPerson, @PathVariable Long passengerID) {
        Passenger passenger = this.passenger_repository.findById(passengerID).get();
        newPerson.setPassenger(passenger);
        return this.person_repository.save(newPerson);
    }



    @PutMapping("/people/{personID}/passengers/{passengerID}")     //UPDATE
    Person replacePerson(@RequestBody Person person, @PathVariable Long personID, @PathVariable Long passengerID)
    {
        Passenger passenger = passenger_repository.findById(passengerID).get();

        Person foundPerson = this.person_repository.findById(personID).get();
        foundPerson.setFirstName(person.getFirstName());
        foundPerson.setLastName(person.getLastName());
        foundPerson.setDateOfBirth(person.getDateOfBirth());
        foundPerson.setGender(person.getGender());
        foundPerson.setPhoneNumber(person.getPhoneNumber());
        foundPerson.setPassenger(passenger);
        return this.person_repository.save(foundPerson);

    }

    @DeleteMapping("/people/{id}")  //DELETE
    void deletePerson(@PathVariable Long id) {
        person_repository.deleteById(id);
    }


    @GetMapping("/people/average-distance-of-bus-routes")  // A3 statistic
    @CrossOrigin(origins = "*")
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
