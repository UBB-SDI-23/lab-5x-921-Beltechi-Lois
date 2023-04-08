package com.example.busManagement.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO_getById_LuggageBusRoutes {
    private long id;
    private  String firstName;
    private  String lastName;
    private  String nationality;
    private  String gender;
    private  String phoneNumber;

    private List<LuggageNoPersonDTO> luggages = new ArrayList<>();  // without Person that appears in Luggage !!

    private List<Bus_Route_TicketDTO> busroutes = new ArrayList<>();

}
