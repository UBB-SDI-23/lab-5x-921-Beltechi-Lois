package com.example.busManagement.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private long id;
    private PersonDTO person;
    private Bus_RouteDTO bus_route;

    private  String payment_method;
    private  String seat_number;

}
