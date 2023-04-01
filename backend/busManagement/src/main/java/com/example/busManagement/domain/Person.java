package com.example.busManagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="mt_person")
public class Person{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="person_id")
    private long id;

    @OneToMany(mappedBy = "person",cascade = CascadeType.REMOVE)
    @JsonIgnore
    Set<Ticket> tickets;


//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "passenger_id") // FK
//    private Passenger passenger;

    @OneToOne( mappedBy = "person", cascade = CascadeType.ALL,optional = false)//, cascade = CascadeType.ALL)
    @JoinColumn(name="person_id",nullable = false)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private Passenger passenger;


    @Column(name="person_firstName")
    private  String firstName;

    @Column(name="person_lastName")
    private  String lastName;

    @Column(name="person_dateOfBirth")
    private  String dateOfBirth;

    @Column(name="person_gender")
    private  String gender;

    @Column(name="person_phoneNumber")
    private  String phoneNumber;


    public Person(String firstName, String lastName, String dateOfBirth, String gender, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public Person(long id, String firstName, String lastName, String dateOfBirth, String gender, String phoneNumber) {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public Person() {

    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public Long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(phoneNumber, person.phoneNumber) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(dateOfBirth, person.dateOfBirth) && Objects.equals(gender, person.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, dateOfBirth, gender, phoneNumber);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}