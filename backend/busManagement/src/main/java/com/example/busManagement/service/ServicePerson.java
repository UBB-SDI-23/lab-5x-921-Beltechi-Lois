package com.example.busManagement.service;

import com.example.busManagement.domain.DTO.*;
import com.example.busManagement.domain.Luggage;
import com.example.busManagement.domain.Person;
import com.example.busManagement.domain.Ticket;
import com.example.busManagement.exception.PersonNotFoundException;
import com.example.busManagement.repository.IRepositoryLuggage;
import com.example.busManagement.repository.IRepositoryPerson;
import com.example.busManagement.repository.IRepositoryTicket;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServicePerson {
    private final IRepositoryPerson person_repository;
    private final IRepositoryLuggage luggage_repository;

    private final IRepositoryTicket ticket_repository;



    public ServicePerson(IRepositoryPerson person_repository, IRepositoryLuggage luggage_repository, IRepositoryTicket ticket_repository) {
        this.person_repository = person_repository;
        this.luggage_repository = luggage_repository;
        this.ticket_repository = ticket_repository;
    }


    public List<PersonAllLuggagesDTO> getAllPeople(PageRequest pr) {

//        List<Person> people = person_repository.findAll();
//        Map<Long, Integer> personToBusRoutesMap = new HashMap<>();
//
//        for (Person person : people) {
//            int count = 0;
//            for (Ticket ticket : person.getTickets()) {
//                if(person.getId()==ticket.getPerson().getId()){  // Current Person == appears in current ticket
//                    count++;
//                }
//            }
//            personToBusRoutesMap.put(person.getId(), count);
//        }
//
//        ModelMapper modelMapper = new ModelMapper();
//
//        return people.stream()
//                .map(person -> {
//                    PersonDTOWith_noBusTaken dto = modelMapper.map(person, PersonDTOWith_noBusTaken.class);
//                    dto.setNumBusRoutesTaken(personToBusRoutesMap.get(person.getId()));
//                    return dto;
//                })
//                .collect(Collectors.toList());

        Page<Person> peoplePage = person_repository.findAll(pr);
        ModelMapper modelMapper = new ModelMapper();


        return peoplePage.stream()
                .map(person -> {
                    PersonAllLuggagesDTO dto = modelMapper.map(person, PersonAllLuggagesDTO.class);
//                    int luggageCount = person.getLuggage().size();
                    int luggageCount = luggage_repository.countByPersonId(person.getId());
                    dto.setNoOfLuggages(luggageCount);
                    return dto;
                })
                .collect(Collectors.toList());
    }


    // Luggage + Tickets[NO  of busroutes]
    public PersonDTO_getById_Luggage getByIdPerson(String id) {

        Long personId = Long.parseLong(id);



//        Optional<Person> personOptional = person_repository.findById(id);
//
//        if (personOptional.isEmpty()) {
//            throw new PersonNotFoundException(id);
//        }
//
//        Person person = personOptional.get();
//
//
//        List<Bus_Route_TicketDTO> busRoutes = new ArrayList<>();
//        //List<Ticket> tickets = person.getTickets();       ///HERE QUERY!!
//
//        List<Ticket> tickets=ticket_repository.findByPersonId(person.getId());
//
//        if (tickets != null) {
//            for (Ticket ticket : tickets) {
//                if (ticket.getPerson().getId() == id) {
//                    Bus_Route_TicketDTO busRoute = new Bus_Route_TicketDTO();
//                    busRoute.setId(ticket.getBus_route().getId());
//                    busRoute.setBus_name(ticket.getBus_route().getBus_name());
//                    busRoute.setRoute_type(ticket.getBus_route().getRoute_type());
//                    busRoute.setDeparture_hour(ticket.getBus_route().getDeparture_hour());
//                    busRoute.setArrival_hour(ticket.getBus_route().getArrival_hour());
//                    busRoute.setDistance(ticket.getBus_route().getDistance());
//                    busRoute.setSeatNumber(ticket.getSeat_number());
//                    busRoute.setPurchase_method(ticket.getPayment_method());
//                    busRoutes.add(busRoute);
//                }
//            }
//        }
//
//        ModelMapper modelMapper = new ModelMapper();
//
//        // Retrieve the luggage information for the person
//        List<Luggage> luggagePersonList=luggage_repository.findLuggagesByPersonId(person.getId());
//
//        //and map it to a list of LuggageNoPersonDTO objects
//        List<LuggageNoPersonDTO> luggageList=luggagePersonList.stream()
//                        .map(luggage -> modelMapper.map(luggage, LuggageNoPersonDTO.class))
//                .collect(Collectors.toList());
//
//
//        modelMapper.typeMap(Person.class, PersonDTO_getById_LuggageBusRoutes.class)
//                .addMappings(mapper -> {
//                    mapper.skip(PersonDTO_getById_LuggageBusRoutes::setBusroutes);
//                    mapper.skip(PersonDTO_getById_LuggageBusRoutes::setLuggages);
//                });
//
//        PersonDTO_getById_LuggageBusRoutes personDTOid = modelMapper.map(person, PersonDTO_getById_LuggageBusRoutes.class);
//        personDTOid.setBusroutes(busRoutes);
//        personDTOid.setLuggages(luggageList);
//        return personDTOid;

        //Method2
        Optional<Person> personOptional = person_repository.findById(personId);

        if (personOptional.isEmpty()) {
            throw new PersonNotFoundException(personId);
        }

        Person person = personOptional.get();

        List<LuggageNoPersonDTO> luggageList = person.getLuggage().stream()   //Get Luggage query
                .map(luggage -> new ModelMapper().map(luggage, LuggageNoPersonDTO.class))
                .collect(Collectors.toList());

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(Person.class, PersonDTO_getById_Luggage.class)
                .addMappings(mapper -> mapper.skip(PersonDTO_getById_Luggage::setLuggages));

        PersonDTO_getById_Luggage personDTO = modelMapper.map(person, PersonDTO_getById_Luggage.class);
        personDTO.setLuggages(luggageList);
        return personDTO;

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


    public List<PersonAverageLugWeightDTO> getPeopleOrderedByAverageWeightOfLuggages(PageRequest pr) {
        List<PersonAverageLugWeightDTO> result = new ArrayList<>();

        Page<Person> people = person_repository.findAll(pr);

        for (Person person : people) {
            int totalCount = 0;
            int totalWeight = 0;

            List<Luggage> luggages = luggage_repository.findLuggagesByPersonId(person.getId());

            for (Luggage luggage : luggages) {
                int weight = luggage.getWeight();
                totalWeight += weight;
                totalCount++;
            }

            if (totalCount > 0) {
                int averageWeight = totalWeight / totalCount;
                PersonAverageLugWeightDTO dto = new PersonAverageLugWeightDTO(
                        person.getId(), person.getFirstName(), person.getLastName(), averageWeight);
                result.add(dto);
            }
        }

        Collections.sort(result, Comparator.comparing(PersonAverageLugWeightDTO::getAverageWeight));
        return result;
    }

    public long getCount() {
        return person_repository.count();
    }

    public List<Person> getPeopleIdsAutocomplete(String query) {
        PageRequest pr = PageRequest.of(0,500);

        Page<Person> people = person_repository.findAll(pr);

        return people.stream()
//                .filter(person -> String.valueOf(person.getId()).startsWith(query))
                .filter(person -> person.getLastName().toLowerCase().contains(query.toLowerCase())).limit(20)
                .limit(20)
                .collect(Collectors.toList());
    }
}
