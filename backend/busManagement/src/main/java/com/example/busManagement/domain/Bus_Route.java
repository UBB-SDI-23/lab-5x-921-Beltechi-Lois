package com.example.busManagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="mt_bus_route")
public class Bus_Route {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="bus_route_id")
    private long id;

    @OneToMany(mappedBy = "bus_route",cascade = CascadeType.REMOVE)
    @JsonIgnore
    Set<Ticket> tickets;


    @Column(name="bus_name")
    private  String bus_name;

    @Column(name="route_type")
    private  String route_type;

    @NotBlank(message = "Departure hour field is mandatory")
    @Column(name="departure_hour")
    private  String departure_hour;

    @NotBlank(message = "Arrival field is mandatory")
    @Column(name="arrival_hour")
    private  String arrival_hour;

    @Column(name="distance")
    private  String distance;

    public Bus_Route(String bus_name, String route_type, String departure_hour, String arrival_hour, String distance) {
        this.bus_name = bus_name;
        this.route_type = route_type;
        this.departure_hour = departure_hour;
        this.arrival_hour = arrival_hour;
        this.distance = distance;
    }

    public Bus_Route(long id, String bus_name, String route_type, String departure_hour, String arrival_hour, String distance) {
        this.id = id;
        this.bus_name = bus_name;
        this.route_type = route_type;
        this.departure_hour = departure_hour;
        this.arrival_hour = arrival_hour;
        this.distance = distance;
    }

    public Bus_Route() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bus_Route bus_route = (Bus_Route) o;
        return id == bus_route.id && Objects.equals(bus_name, bus_route.bus_name) && Objects.equals(route_type, bus_route.route_type) && Objects.equals(departure_hour, bus_route.departure_hour) && Objects.equals(arrival_hour, bus_route.arrival_hour) && Objects.equals(distance, bus_route.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bus_name, route_type, departure_hour, arrival_hour, distance);
    }

    @Override
    public String toString() {
        return "Bus_Route{" +
                "id=" + id +
                ", bus_name='" + bus_name + '\'' +
                ", route_type='" + route_type + '\'' +
                ", departure_hour='" + departure_hour + '\'' +
                ", arrival_hour='" + arrival_hour + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }
}