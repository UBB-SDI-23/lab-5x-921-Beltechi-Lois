package com.example.busManagement.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LuggagePersonNationalityDTO {

    private long luggage_id;
    private int weight;
    private String priority;
    private String status;
    private String person_nationality;
}
