package com.example.busManagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank(message = "Bus_Name field is mandatory")
    private  String bus_name;

    @NotBlank(message = "Route type field is mandatory")
    private  String route_type;

    @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$", message = "Invalid hour format. Please use HH:MM.")
    private String departure_hour;

    @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$", message = "Invalid hour format. Please use HH:MM.")
    private String arrival_hour;

    @NotBlank(message = "Distance field is mandatory")
    private  String distance;


}