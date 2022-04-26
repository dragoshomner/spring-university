package com.example.project.dtos;

import lombok.Data;

@Data
public class TrainDto {
    private Long id;
    private String code;
    private Integer numberOfSeats;

    public TrainDto() {}

    public TrainDto(Long id, String code, Integer numberOfSeats) {
        this.id = id;
        this.code = code;
        this.numberOfSeats = numberOfSeats;
    }
}
