package com.example.busManagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;

@Entity
@Table(name="mt_ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ticket_id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)//, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id",nullable = false, foreignKey = @ForeignKey(name = "FK_person_id"))
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Person person;

    @ManyToOne(fetch = FetchType.EAGER, optional = false) //cascade = CascadeType.ALL)
    @JoinColumn(name = "bus_route_id",nullable = false, foreignKey = @ForeignKey(name = "FK_bus_route_id"))
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Bus_Route bus_route;

    @Column(name="purchase_date")
    private  String purchase_date;

    @Column(name="seat_number")
    private  String seat_number;

    public Ticket(String purchase_date, String seat_number) {
        this.purchase_date = purchase_date;
        this.seat_number = seat_number;
    }

    public Ticket() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Bus_Route getBus_route() {
        return bus_route;
    }

    public void setBus_route(Bus_Route bus_route) {
        this.bus_route = bus_route;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(String seat_number) {
        this.seat_number = seat_number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && Objects.equals(person, ticket.person) && Objects.equals(bus_route, ticket.bus_route) && Objects.equals(purchase_date, ticket.purchase_date) && Objects.equals(seat_number, ticket.seat_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, person, bus_route, purchase_date, seat_number);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", purchase_date='" + purchase_date + '\'' +
                ", seat_number='" + seat_number + '\'' +
                '}';
    }
}
