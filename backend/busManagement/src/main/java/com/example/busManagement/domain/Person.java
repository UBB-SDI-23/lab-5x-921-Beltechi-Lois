package com.example.busManagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="person")
public class Person{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="person_id")
    private long id;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    List<Ticket> tickets;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    List<Luggage> luggage;
    private  String firstName;
    private  String lastName;
    private  String nationality;
    private  String gender;
    private  String phoneNumber;

    public Person(String firstName, String lastName, String nationality, String gender, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }
}