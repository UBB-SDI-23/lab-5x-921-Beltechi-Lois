package com.example.busManagement.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LuggageDTO {

    private long id;
    private String type;
    private int weight;
    private String color;
    private String priority;
    private String status;
    private String description;
    private PersonDTO person;

}

