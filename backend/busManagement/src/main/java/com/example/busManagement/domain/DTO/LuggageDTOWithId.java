package com.example.busManagement.domain.DTO;

public class LuggageDTOWithId {

    private long id;
    private int busNumber;
    private int weight;
    private int size;
    private String owner;
    private String status;
    Integer passenger_Id;


    public LuggageDTOWithId(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Integer getPassenger_Id() {
        return passenger_Id;
    }

    public void setPassenger_Id(Integer passenger_Id) {
        this.passenger_Id = passenger_Id;
    }
}


