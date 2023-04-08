package com.example.busManagement.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bus_RouteDTO {

    private long id;
    private  String bus_name;
    private  String route_type;
    private  String departure_hour;
    private  String arrival_hour;
    private  String distance;

}
