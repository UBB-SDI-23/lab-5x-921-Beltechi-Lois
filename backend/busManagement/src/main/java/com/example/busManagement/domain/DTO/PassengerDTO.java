package com.example.busManagement.domain.DTO;

import java.util.Objects;

public class PassengerDTO{
    private long id;
    private String timesTravelled;
    private  String firstName;
    private  String lastName;
    private  String dateOfBirth;
    private  String gender;
    private  String phoneNumber;

    public PassengerDTO(String timesTravelled, String firstName, String lastName, String dateOfBirth, String gender, String phoneNumber) {
        //this.id = id;
        this.timesTravelled = timesTravelled;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public PassengerDTO(long id, String timesTravelled, String firstName, String lastName, String dateOfBirth, String gender, String phoneNumber) {
        this.id = id;
        this.timesTravelled = timesTravelled;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public PassengerDTO() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public String getTimesTravelled() {
        return timesTravelled;
    }

    public void setTimesTravelled(String timesTravelled) {
        this.timesTravelled = timesTravelled;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassengerDTO that = (PassengerDTO) o;
        return id == that.id && Objects.equals(timesTravelled, that.timesTravelled) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(gender, that.gender) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timesTravelled, firstName, lastName, dateOfBirth, gender, phoneNumber);
    }

    @Override
    public String toString() {
        return "PassengerDTO{" +
                "id=" + id +
                ", timesTravelled='" + timesTravelled + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }


}
