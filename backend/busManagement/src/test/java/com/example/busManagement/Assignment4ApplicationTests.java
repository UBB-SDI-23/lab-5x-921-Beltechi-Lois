package com.example.busManagement;

import com.example.busManagement.controller.ControllerLuggage;
import com.example.busManagement.controller.ControllerPassenger;
import com.example.busManagement.controller.ControllerPerson;
import com.example.busManagement.domain.*;
import com.example.busManagement.domain.DTO.PassengerAverageLugWeightDTO;
import com.example.busManagement.domain.DTO.PersonAverageDistanceDTO;
import com.example.busManagement.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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


/////



import static org.hamcrest.Matchers.hasSize;


import static org.mockito.ArgumentMatchers.any;

//import org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@SpringBootTest
class Assignment4ApplicationTests {

    @Autowired
    private ControllerPerson person_controller;

    @Autowired
    private ControllerPassenger passenger_controller;


    @Autowired
    private ControllerLuggage luggage_controller;

    @MockBean
    private IRepositoryPassenger passenger_repository;

    @MockBean
    private IRepositoryPerson person_repository;

    @MockBean
    private IRepositoryBusRoute bus_repository;

    @MockBean
    private IRepositoryLuggage luggage_repository;

    @MockBean
    private IRepositoryTicket ticket_repository;



    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;





//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        this.mockMvc=MockMvcBuilders.standaloneSetup(person_controller).build();
//    }

    @Test
    public void testPeopleUrl() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/people"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }



    @Test
    void testPersonModelName() {
        Person person = new Person(1, "Alina", "Bucur", "1999/10/10", "Female", "0743591501");
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

    }






    // UNITTEST for the 2 requirements

    @Test
    void getPersonStatisticTest() {
        Person person1 = new Person();
        person1.setId(1L);
        person1.setFirstName("Alina");
        person1.setLastName("Bucur");
        person1.setDateOfBirth("1999/03/22");
        person1.setGender("Female");
        person1.setPhoneNumber("0723460325");


        Person person2 = new Person();
        person2.setFirstName("Dragos");
        person2.setLastName("Popa");
        person2.setDateOfBirth("2000/10/10");
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
        ticket1.setPurchase_date("2020/10/10");


        Ticket ticket2= new Ticket();
        ticket1.setId(2L);
        ticket1.setPerson(person2);
        ticket1.setBus_route(bus2);
        ticket1.setSeat_number("124");
        ticket1.setPurchase_date("2020/12/12");


        Set<Ticket> tickets_of_person1= new HashSet<>();
        tickets_of_person1.add(ticket1);
        person1.setTickets(tickets_of_person1);

        Set<Ticket> tickets_of_person2= new HashSet<>();
        tickets_of_person2.add(ticket2);
        person2.setTickets(tickets_of_person2);

        Set<Ticket> tickets_of_busroute1= new HashSet<>();
        tickets_of_busroute1.add(ticket1);
        bus1.setTickets(tickets_of_busroute1);

        Set<Ticket> tickets_of_busroute2= new HashSet<>();
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
    void getPassengerLuggageStatisticTest() {

        Passenger passenger1=new Passenger();
        passenger1.setId(1L);
        passenger1.setTimesTravelled("12");
        passenger1.setGender("Female");
        passenger1.setDateOfBirth("1999/10/10");
        passenger1.setLastName("Bucur");
        passenger1.setFirstName("Alina");
        passenger1.setPhoneNumber("0723460325");
        //passenger1.setLuggages();

        Passenger passenger2=new Passenger();
        passenger2.setId(2L);
        passenger2.setTimesTravelled("123");
        passenger2.setGender("Male");
        passenger2.setDateOfBirth("2000/12/12");
        passenger2.setLastName("Popa");
        passenger2.setFirstName("Dragos");
        passenger2.setPhoneNumber("0743591501");
        //passenger1.setLuggages();

        Luggage luggage1=new Luggage();
        luggage1.setId(1L);
        luggage1.setBusNumber(123);
        luggage1.setWeight(3);
        luggage1.setOwner("Alina Bucur");
        luggage1.setSize(5);
        luggage1.setStatus("unchecked");
        //luggage1.setPassenger();

        Luggage luggage2=new Luggage();
        luggage2.setId(2L);
        luggage2.setBusNumber(124);
        luggage2.setWeight(5);
        luggage2.setOwner("Dragos Popa");
        luggage2.setSize(7);
        luggage2.setStatus("checked");
        //luggage2.setPassenger();

        luggage1.setPassenger(passenger1);
        luggage2.setPassenger(passenger2);

        Set<Luggage> luggages_of_passenger1=new HashSet<>();
        luggages_of_passenger1.add(luggage1);
        passenger1.setLuggages(luggages_of_passenger1);

        Set<Luggage> luggages_of_passenger2=new HashSet<>();
        luggages_of_passenger2.add(luggage2);
        passenger2.setLuggages(luggages_of_passenger2);

        passenger_repository.save(passenger1);
        passenger_repository.save(passenger2);
        luggage_repository.save(luggage1);
        luggage_repository.save(luggage2);

        when(passenger_controller.getPassengersOrderedByAverageLuggageWeight()).thenReturn(
                Stream.of(new PassengerAverageLugWeightDTO(
                                1,"Alina","Bucur",3),
                        new PassengerAverageLugWeightDTO(
                                2,"Dragos","Popa",5)
                ).collect(Collectors.toList()));
        System.out.println("Test 2 passed");
    }



    @Test
    void getFilteredLuggagesA2() {

        Passenger passenger1=new Passenger();
        passenger1.setId(1L);
        passenger1.setTimesTravelled("12");
        passenger1.setGender("Female");
        passenger1.setDateOfBirth("1999/10/10");
        passenger1.setLastName("Bucur");
        passenger1.setFirstName("Alina");
        passenger1.setPhoneNumber("0723460325");
        //passenger1.setLuggages();

        Passenger passenger2=new Passenger();
        passenger2.setId(2L);
        passenger2.setTimesTravelled("123");
        passenger2.setGender("Male");
        passenger2.setDateOfBirth("2000/12/12");
        passenger2.setLastName("Popa");
        passenger2.setFirstName("Dragos");
        passenger2.setPhoneNumber("0743591501");
        //passenger1.setLuggages();

        Luggage luggage1=new Luggage();
        luggage1.setId(1L);
        luggage1.setBusNumber(123);
        luggage1.setWeight(3);
        luggage1.setOwner("Alina Bucur");
        luggage1.setSize(5);
        luggage1.setStatus("unchecked");
        //luggage1.setPassenger();

        Luggage luggage2=new Luggage();
        luggage2.setId(2L);
        luggage2.setBusNumber(124);
        luggage2.setWeight(5);
        luggage2.setOwner("Dragos Popa");
        luggage2.setSize(7);
        luggage2.setStatus("checked");
        //luggage2.setPassenger();

        luggage1.setPassenger(passenger1);
        luggage2.setPassenger(passenger2);

        Set<Luggage> luggages_of_passenger1=new HashSet<>();
        luggages_of_passenger1.add(luggage1);
        passenger1.setLuggages(luggages_of_passenger1);

        Set<Luggage> luggages_of_passenger2=new HashSet<>();
        luggages_of_passenger2.add(luggage2);
        passenger2.setLuggages(luggages_of_passenger2);

        passenger_repository.save(passenger1);
        passenger_repository.save(passenger2);
        luggage_repository.save(luggage1);
        luggage_repository.save(luggage2);



        when(luggage_controller.higherThan(3)).thenReturn(
                Stream.of(new Luggage(1,passenger2,124,5,7,
                        "Dragos Popa","checked")

                ).collect(Collectors.toList()));
        System.out.println("Test 2 passed");
    }


    }
