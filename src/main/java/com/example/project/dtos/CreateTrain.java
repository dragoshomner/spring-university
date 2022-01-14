package com.example.project.dtos;

import lombok.Data;

@Data
public class CreateTrain {
    private String code;
    private Integer numberOfSeats;

    public CreateTrain() {}

    public CreateTrain(String code, Integer numberOfSeats) {
        this.code = code;
        this.numberOfSeats = numberOfSeats;
    }
}
