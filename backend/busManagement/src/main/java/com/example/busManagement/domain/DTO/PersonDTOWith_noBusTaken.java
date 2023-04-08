package com.example.busManagement.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTOWith_noBusTaken {

    private long id;
    private  String firstName;
    private  String lastName;
    private  String nationality;
    private  String gender;
    private  String phoneNumber;
    private int numBusRoutesTaken;

}
