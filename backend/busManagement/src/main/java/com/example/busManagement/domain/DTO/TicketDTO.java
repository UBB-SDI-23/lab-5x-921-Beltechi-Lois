package com.example.busManagement.domain.DTO;

import com.example.busManagement.domain.DTO.Bus_RouteDTO;
import com.example.busManagement.domain.DTO.PersonDTO;

public class TicketDTO {
    private long id;
    private PersonDTO person;
    private Bus_RouteDTO bus_route;

    private  String purchase_date;
    private  String seat_number;

    public TicketDTO(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    public Bus_RouteDTO getBus_route() {
        return bus_route;
    }

    public void setBus_route(Bus_RouteDTO bus_route) {
        this.bus_route = bus_route;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(String seat_number) {
        this.seat_number = seat_number;
    }
}
