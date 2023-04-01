package com.example.busManagement.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.Objects;
import java.util.Set;

public class Bus_RouteDTO {

    private long id;
    private  String bus_name;
    private  String route_type;
    private  String departure_hour;
    private  String arrival_hour;
    private  String distance;

    public Bus_RouteDTO(){

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bus_RouteDTO that = (Bus_RouteDTO) o;
        return id == that.id && Objects.equals(bus_name, that.bus_name) && Objects.equals(route_type, that.route_type) && Objects.equals(departure_hour, that.departure_hour) && Objects.equals(arrival_hour, that.arrival_hour) && Objects.equals(distance, that.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bus_name, route_type, departure_hour, arrival_hour, distance);
    }

    @Override
    public String toString() {
        return "Bus_RouteDTO{" +
                "id=" + id +
                ", bus_name='" + bus_name + '\'' +
                ", route_type='" + route_type + '\'' +
                ", departure_hour='" + departure_hour + '\'' +
                ", arrival_hour='" + arrival_hour + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }
}
