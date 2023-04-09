package com.example.busManagement.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LuggagePersonDTO {

    long luggage_id;
    String color;
    int noOfPeople;
}
