package com.example.busManagement.domain.DTO;

public class PersonDTOWithId_1_1 {

    private long id;
    private  String firstName;
    private  String lastName;
    private  String dateOfBirth;
    private  String gender;
    private  String phoneNumber;
    private Integer passengerId;

    private int numBusRoutesTaken;

    public int getNumBusRoutesTaken() {
        return numBusRoutesTaken;
    }

    public void setNumBusRoutesTaken(int numBusRoutesTaken) {
        this.numBusRoutesTaken = numBusRoutesTaken;
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

    public Integer getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Integer passengerId) {
        this.passengerId = passengerId;
    }

}
