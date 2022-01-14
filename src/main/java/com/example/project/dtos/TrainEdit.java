package com.example.project.dtos;

import lombok.Data;

@Data
public class TrainEdit {
    public Integer numberOfSeats;

    public TrainEdit(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
