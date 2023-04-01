package com.example.busManagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import java.util.Objects;


@Entity
@Table(name="mt_luggage")
public class Luggage {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="luggage_id")
    private long id;

    //@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "passenger_id", nullable = false, foreignKey = @ForeignKey(name = "FK_passenger_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Passenger passenger;


    @Column(name="luggage_busNumber")
    private int busNumber;

    @NotBlank(message = "Weight field is mandatory")
    @Column(name="luggage_weight")
    private int weight;

    @Column(name="luggage_size")
    private int size;

    @NotBlank(message = "Owner field is mandatory")
    @Column(name="luggage_owner")
    private String owner;

    @Column(name="luggage_status")
    private String status;


    public Luggage() {
    }

    public Luggage(long id, Passenger passenger, int busNumber, int weight, int size, String owner, String status) {
        this.id = id;
        this.passenger = passenger;
        this.busNumber = busNumber;
        this.weight = weight;
        this.size = size;
        this.owner = owner;
        this.status = status;
    }

    public Luggage(int busNumber, int weight, int size, String owner, String status) {
        //this.passenger = passenger;
        this.busNumber = busNumber;
        this.weight = weight;
        this.size = size;
        this.owner = owner;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public int getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(int busNumber) {
        this.busNumber = busNumber;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Luggage luggage = (Luggage) o;
        return id == luggage.id && busNumber == luggage.busNumber && weight == luggage.weight && size == luggage.size && Objects.equals(passenger, luggage.passenger) && Objects.equals(owner, luggage.owner) && Objects.equals(status, luggage.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, passenger, busNumber, weight, size, owner, status);
    }

    @Override
    public String toString() {
        return "Luggage{" +
                "id=" + id +
                ", passenger=" + passenger +
                ", busNumber=" + busNumber +
                ", weight=" + weight +
                ", size=" + size +
                ", owner='" + owner + '\'' +
                ", status='" + status + '\'' +
                '}';
    }



}