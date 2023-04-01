package com.example.busManagement;


import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.busManagement.controller.ControllerLuggage;
import com.example.busManagement.controller.ControllerPassenger;
import com.example.busManagement.controller.ControllerPerson;
import com.example.busManagement.domain.*;
import com.example.busManagement.repository.IRepositoryLuggage;
import com.example.busManagement.repository.IRepositoryPassenger;
import com.example.busManagement.repository.IRepositoryPerson;
import net.minidev.json.JSONObject;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@ExtendWith(MockitoExtension.class)
public class TestEndPoints {

    @Mock
    private IRepositoryPerson peopleRepository;

    @InjectMocks
    private ControllerPerson peopleController;



    @Mock
    private IRepositoryPassenger passengerRepository;

    @Mock
    private IRepositoryLuggage luggageRepository;

    @InjectMocks
    private ControllerPassenger passengerController;

    @InjectMocks
    private ControllerLuggage luggagesController;



    @Test
    public void testPeopleUrlWithId() throws Exception {
        Person person = new Person(1, "John", "Doe", "1990-01-01", "Male", "1234567890");
        Mockito.when(peopleRepository.findById(1L)).thenReturn(Optional.of(person));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(peopleController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/people/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andExpect(MockMvcResultMatchers.jsonPath("$.name", MockMvcResultMatchers.equalTo("John")));
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("John")));

    }

    @Test
    public void testGetPassengersOrderedByAverageLuggageWeight() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(passengerController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/passengers/average-luggageWeight-of-passenger"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }


    @Test
    public void testHigherThanEndpoint() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(luggagesController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/luggages/higherThanGivenWeight/25"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //getPeopleOrderedByAverageDistanceOfBusRoutes//

    @Test
    public void testgetPeopleOrderedByAverageDistanceOfBusRoutes() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(peopleController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/people/average-distance-of-bus-routes"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }



}



