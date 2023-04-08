package com.example.busManagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="luggage")
public class Luggage {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @JsonIgnore
    private Person person;
    private String type;   //suitcase, backpack
    @Min(value = 1, message = "Weight field >= 1")
    private int weight;
    private String color;
    @NotBlank(message = "Priority field is mandatory")
    private String priority;
    private String status;     //checked / unchecked


}