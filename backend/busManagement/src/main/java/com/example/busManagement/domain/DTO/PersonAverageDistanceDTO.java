package com.example.busManagement.domain.DTO;

public class PersonAverageDistanceDTO {

    private long personId;
    private String firstName;
    private String lastName;
    private double averageDistance;


    public PersonAverageDistanceDTO(long personId, String firstName, String lastName, double averageDistance) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.averageDistance = averageDistance;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
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

    public double getAverageDistance() {
        return averageDistance;
    }

    public void setAverageDistance(double averageDistance) {
        this.averageDistance = averageDistance;
    }
}
