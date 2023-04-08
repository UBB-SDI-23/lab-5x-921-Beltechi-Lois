package com.example.busManagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="bus_route")
public class Bus_Route {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="bus_route_id")
    private long id;

    @OneToMany(mappedBy = "bus_route", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    List<Ticket> tickets;

    private  String bus_name;
    private  String route_type;
    @NotBlank(message = "Departure hour field is mandatory")
    private  String departure_hour;

    @NotBlank(message = "Arrival field is mandatory")
    private  String arrival_hour;
    private  String distance;


}