package com.example.busManagement.domain;

public class PassengerAverageLugWeightDTO {

    private long id;
    private  String firstName;
    private  String lastName;

    private double AverageLuggageWeight;

    public PassengerAverageLugWeightDTO(long id, String firstName, String lastName, double averageLuggageWeight) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        AverageLuggageWeight = averageLuggageWeight;
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

    public double getAverageLuggageWeight() {
        return AverageLuggageWeight;
    }

    public void setAverageLuggageWeight(double averageLuggageWeight) {
        AverageLuggageWeight = averageLuggageWeight;
    }


}
