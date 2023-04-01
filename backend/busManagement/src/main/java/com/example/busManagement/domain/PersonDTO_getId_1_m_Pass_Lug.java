package com.example.busManagement.domain;

import java.util.HashSet;
import java.util.Set;

public class PersonDTO_getId_1_m_Pass_Lug {
    private long id;
    private  String firstName;
    private  String lastName;
    private  String dateOfBirth;
    private  String gender;
    private  String phoneNumber;
    private PassengerDTO passenger; // fara Luggages da si fara LuggagesId...


    //TicketDTOWithId tickets; // Int la FK si integer la FK // nu vrei si inner details..
    // inner details: TicketDTO
    //private Set<TicketDTOWithId> tickets = new HashSet<>(); //stergem
    private Set<Bus_Route_TicketDTO> busroutes = new HashSet<>();


    public Set<Bus_Route_TicketDTO> getBusroutes() {
        return busroutes;
    }


    public PersonDTO_getId_1_m_Pass_Lug() {
    }

    public PersonDTO_getId_1_m_Pass_Lug(long id, String firstName, String lastName, String dateOfBirth, String gender, String phoneNumber, PassengerDTO passenger, Set<Bus_Route_TicketDTO> busroutes) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.passenger = passenger;
        this.busroutes = busroutes;
    }

    public void setBusroutes(Set<Bus_Route_TicketDTO> busroutes) {
        this.busroutes = busroutes;
    }

    public PassengerDTO getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerDTO passenger) {
        this.passenger = passenger;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


//    public Set<TicketDTOWithId> getTickets() {
//        return tickets;
//    }
//
//    public void setTickets(Set<TicketDTOWithId> tickets) {
//        this.tickets = tickets;
//    }
}
