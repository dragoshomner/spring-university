package com.example.project.dtos;

import lombok.Data;
import net.bytebuddy.description.field.FieldDescription;

import java.time.LocalDateTime;

@Data
public class TrainView {
    private String code;
    private Integer numberOfSeats;
    private LocalDateTime createdAt;

    public TrainView(String code, Integer numberOfSeats) {
        this.code = code;
        this.numberOfSeats = numberOfSeats;
    }
}
