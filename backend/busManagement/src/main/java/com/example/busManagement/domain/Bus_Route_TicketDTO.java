package com.example.busManagement.domain;

public class Bus_Route_TicketDTO {

    private long id;
    private  String bus_name;
    private  String route_type;
    private  String departure_hour;
    private  String arrival_hour;
    private  String distance;
    private String seatNumber;

    private String date;

    public Bus_Route_TicketDTO(long id, String bus_name, String route_type, String departure_hour, String arrival_hour, String distance, String seatNumber, String date) {
        this.id = id;
        this.bus_name = bus_name;
        this.route_type = route_type;
        this.departure_hour = departure_hour;
        this.arrival_hour = arrival_hour;
        this.distance = distance;
        this.seatNumber = seatNumber;
        this.date = date;
    }

    public Bus_Route_TicketDTO() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}
