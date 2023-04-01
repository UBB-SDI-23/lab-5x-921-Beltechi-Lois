package com.example.busManagement.domain;

import java.util.HashSet;
import java.util.Set;

public class BusRouteDTOwithTicketsFK {

    private long id;
    private  String bus_name;
    private  String route_type;
    private  String departure_hour;
    private  String arrival_hour;
    private  String distance;
    //TicketDTOWithId tickets;
   // private Set<TicketDTOWithId> tickets = new HashSet<>(); // BusRoutes have Many Tickets  STERS

    private Set<PersonWithTicketDTO> people=new HashSet<>();

    public Set<PersonWithTicketDTO> getPeople() {
        return people;
    }

    public void setPeople(Set<PersonWithTicketDTO> people) {
        this.people = people;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBus_name() {
        return bus_name;
    }

    public void setBus_name(String bus_name) {
        this.bus_name = bus_name;
    }

    public String getRoute_type() {
        return route_type;
    }

    public void setRoute_type(String route_type) {
        this.route_type = route_type;
    }

    public String getDeparture_hour() {
        return departure_hour;
    }

    public void setDeparture_hour(String departure_hour) {
        this.departure_hour = departure_hour;
    }

    public String getArrival_hour() {
        return arrival_hour;
    }

    public void setArrival_hour(String arrival_hour) {
        this.arrival_hour = arrival_hour;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

//    public Set<TicketDTOWithId> getTickets() {
//        return tickets;
//    }
//
//    public void setTickets(Set<TicketDTOWithId> tickets) {
//        this.tickets = tickets;
//    }
}
