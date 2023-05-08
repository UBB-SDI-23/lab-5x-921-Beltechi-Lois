package com.example.busManagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

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

    @NotBlank(message = "FirstName field is mandatory")
    private  String firstName;

    @NotBlank(message = "LastName field is mandatory")
    private  String lastName;

    @NotBlank(message = "Nationality field is mandatory")
    @Length(min = 3, max = 20, message = "Nationality must be between {min} and {max} characters long")
    private  String nationality;

    @NotBlank(message = "Gender field is mandatory")
    private  String gender;
    @Size(min=10, max=10)
    private  String phoneNumber;

    public Person(String firstName, String lastName, String nationality, String gender, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }
}