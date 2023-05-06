package com.example.busManagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    //@NotBlank(message = "Person field is mandatory")
    private Person person;

    @NotBlank(message = "Type field is mandatory")
    private String type;   //suitcase, backpack

    @Positive(message = "Weight field >= 1")
    private int weight;

    @NotBlank(message = "Color field is mandatory")
    private String color;

    @NotBlank(message = "Priority field is mandatory")
    private String priority;

    @NotBlank(message = "Status field is mandatory")
    private String status;     //checked / unchecked

    @NotBlank(message = "Description field is mandatory")
    private String description;


}