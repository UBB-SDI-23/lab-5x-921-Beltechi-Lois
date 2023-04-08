package com.example.busManagement.domain.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusRoutesAveragePeopleDTO {

    private long busRouteId;
    private String busName;
    private String busType;
    private int noOfPeopleTransported;

}
