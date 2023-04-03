package com.example.busManagement.domain.DTO;

public class BusRoutesAveragePeopleDTO {

    private long busRouteId;
    private String busName;
    private String busType;
    private int noOfPeopleTransported;

    public BusRoutesAveragePeopleDTO(long busRouteId, String busName, String busType, int noOfPeopleTransported) {
        this.busRouteId = busRouteId;
        this.busName = busName;
        this.busType = busType;
        this.noOfPeopleTransported = noOfPeopleTransported;
    }

    public long getBusRouteId() {
        return busRouteId;
    }

    public void setBusRouteId(long busRouteId) {
        this.busRouteId = busRouteId;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public int getNoOfPeopleTransported() {
        return noOfPeopleTransported;
    }

    public void setNoOfPeopleTransported(int noOfPeopleTransported) {
        this.noOfPeopleTransported = noOfPeopleTransported;
    }
}
