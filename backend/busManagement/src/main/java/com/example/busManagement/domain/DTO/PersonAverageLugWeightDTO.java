package com.example.busManagement.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonAverageLugWeightDTO {
    private long personId;
    private String firstName;
    private String lastName;
    private int averageWeight;
}
