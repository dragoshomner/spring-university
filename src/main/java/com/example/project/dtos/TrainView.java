package com.example.project.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrainView {
    private String code;
    private Integer numberOfSeats;
    private LocalDateTime createdAt;
}
