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
public class BusRouteDTOwithTicketsFK {

    private long id;
    private  String bus_name;
    private  String route_type;
    private  String departure_hour;
    private  String arrival_hour;
    private  String distance;
    private List<PersonWithTicketDTO> people=new ArrayList<>();

}
