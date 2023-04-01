package com.example.busManagement.domain;

public class TicketDTOWithId {

    private long id;
    private Integer personId;
    private Integer bus_routeId;

    private  String purchase_date;
    private  String seat_number;

    public TicketDTOWithId(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getBus_routeId() {
        return bus_routeId;
    }

    public void setBus_routeId(Integer bus_routeId) {
        this.bus_routeId = bus_routeId;
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
}
