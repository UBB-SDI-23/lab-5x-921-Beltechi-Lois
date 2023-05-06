package com.example.busManagement.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTOwithIdCounter {
    private long id;
    private Integer personId;
    private Integer bus_routeId;

    private  String payment_method;
    private  String seat_number;
    //private int ticket_count;
    private int noOfTicketsOfBusRouteId;
}
