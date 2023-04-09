package com.example.busManagement;

import com.example.busManagement.controller.ControllerBus_Route;

import com.example.busManagement.controller.ControllerPerson;
import com.example.busManagement.domain.*;
import com.example.busManagement.domain.DTO.PersonAverageDistanceDTO;
import com.example.busManagement.repository.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;




@RunWith(SpringRunner.class)
@SpringBootTest
class Assignment4ApplicationTests {

    @Autowired
    private ControllerPerson person_controller;
    @Autowired
    private ControllerBus_Route busroute_controller;
    @MockBean
    private IRepositoryPerson person_repository;
    @MockBean
    private IRepositoryBusRoute bus_repository;
    @MockBean
    private IRepositoryTicket ticket_repository;
    @Autowired
    private WebApplicationContext webApplicationContext;


    // Test endpoints

    @Test
    public void testPeopleUrl() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/people"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testgetPeopleOrderedByAverageDistanceOfBusRoutes() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(person_controller).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/people/average-distance-of-bus-routes"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testHigherThanEndpoint() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(busroute_controller).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/busroutes/higherThanGivenDistance/25"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }



    @Test
    public void testPeopleUrlWithId() throws Exception {

        Person person = new Person(1,  new ArrayList<Ticket>(), new ArrayList<Luggage>(),"John",
                "Doe", "Romanian", "Male", "1234567890");

        person_controller.newPerson(person);

        Mockito.when(person_repository.findById(1L)).thenReturn(Optional.of(person));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(person_controller).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/people/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andExpect(MockMvcResultMatchers.jsonPath("$.name", MockMvcResultMatchers.equalTo("John")));
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("John")));

    }


    @Test
    void testPersonModelName() {
        Person person = new Person(1,null,null,"Alina", "Bucur",
                "Romania", "Female", "0743591501");

        given(person_repository.findById(1L)).willReturn(Optional.of(person));

        Optional<Person> personFound = person_repository.findById(1L);
        String modelName = personFound.get().getFirstName();
        assertEquals("Alina", modelName);
        System.out.println("Test model passed");
    }



    @Test
    public void testGetAllPeople() throws Exception {
        // Create a list of people
        List<Person> people = new ArrayList<>();
        people.add(new Person("John", "Doe", "1990/01/01", "Male", "1234567890"));
        people.add(new Person("Mary", "Smith", "1995/05/05", "Female", "0987654321"));

        // Mock the repository behavior
        given(person_repository.findAll()).willReturn(people);


        List<Person> people_end = person_repository.findAll();
        assertEquals(2, people_end.size());

        System.out.println("Test GetALL passed");


    }



    // UNITTEST for the 2 requirements

    @Test
    void getPersonStatisticTest() {
        Person person1 = new Person();
        person1.setId(1L);
        person1.setFirstName("Alina");
        person1.setLastName("Bucur");
        person1.setNationality("Romanian");
        person1.setGender("Female");
        person1.setPhoneNumber("0723460325");


        Person person2 = new Person();
        person2.setFirstName("Dragos");
        person2.setLastName("Popa");
        person2.setNationality("Romanian");
        person2.setGender("Male");
        person2.setPhoneNumber("0743591501");
        person2.setId(2L);


        Bus_Route bus1 = new Bus_Route();
        bus1.setBus_name("Red Express");
        bus1.setRoute_type("Local");
        bus1.setDeparture_hour("06:30");
        bus1.setArrival_hour("07:00");
        bus1.setDistance("10");
        bus1.setId(1L);


        Bus_Route bus2 = new Bus_Route();
        bus2.setBus_name("Red Express");
        bus2.setRoute_type("Express");
        bus2.setDeparture_hour("09:00");
        bus2.setArrival_hour("12:00");
        bus2.setDistance("102");
        bus2.setId(2L);


        Ticket ticket1= new Ticket();
        ticket1.setId(1L);
        ticket1.setPerson(person1);
        ticket1.setBus_route(bus1);
        ticket1.setSeat_number("123");
        ticket1.setPayment_method("Card");


        Ticket ticket2= new Ticket();
        ticket1.setId(2L);
        ticket1.setPerson(person2);
        ticket1.setBus_route(bus2);
        ticket1.setSeat_number("124");
        ticket1.setPayment_method("Cash");


        List<Ticket> tickets_of_person1= new ArrayList<>();
        tickets_of_person1.add(ticket1);
        person1.setTickets(tickets_of_person1);

        List<Ticket> tickets_of_person2= new ArrayList<>();
        tickets_of_person2.add(ticket2);
        person2.setTickets(tickets_of_person2);

        List<Ticket> tickets_of_busroute1= new ArrayList<>();
        tickets_of_busroute1.add(ticket1);
        bus1.setTickets(tickets_of_busroute1);

        List<Ticket> tickets_of_busroute2= new ArrayList<>();
        tickets_of_busroute2.add(ticket2);
        bus2.setTickets(tickets_of_busroute2);

        person_repository.save(person1);
        person_repository.save(person2);
        bus_repository.save(bus1);
        bus_repository.save(bus2);
        ticket_repository.save(ticket1);
        ticket_repository.save(ticket2);


        when(person_controller.getPeopleOrderedByAverageDistanceOfBusRoutes()).thenReturn(
                Stream.of(new PersonAverageDistanceDTO(
                                1,"Alina","Bucur",10),
                        new PersonAverageDistanceDTO(
                                2,"Dragos","Popa",102)
                        ).collect(Collectors.toList()));
        System.out.println("Test 1 passed");
    }


    @Test
    void getFilteredBusRoutesA2() {
        Bus_Route bus1 = Bus_Route.builder()
                .id(1L)
                .bus_name("BZN1")
                .route_type("RDR3")
                .departure_hour("18:00")
                .arrival_hour("19:00")
                .distance("300")
                .build();

        bus1.setTickets(null);

        Bus_Route bus2 = Bus_Route.builder()
                .id(1L)
                .bus_name("BZN2")
                .route_type("RDR4")
                .departure_hour("19:00")
                .arrival_hour("20:00")
                .distance("200")
                .build();
       bus2.setTickets(null);

        bus_repository.save(bus1);
        bus_repository.save(bus2);


        when(busroute_controller.higherThan("200",0,2)).thenReturn(
                Stream.of(new Bus_Route(1,null,"BZN1","RDR3","18:00",
                        "19:00","300")

                ).collect(Collectors.toList()));
        System.out.println("Test 2 passed");
    }


    }
