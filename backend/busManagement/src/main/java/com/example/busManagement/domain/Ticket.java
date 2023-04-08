package com.example.busManagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ticket_id")
    private long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @JsonIgnore
    private Person person;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_route_id")
    @JsonIgnore
    private Bus_Route bus_route;

    private  String payment_method;
    private  String seat_number;


}