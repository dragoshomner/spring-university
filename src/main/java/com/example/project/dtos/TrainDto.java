package com.example.project.dtos;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class TrainDto {
    private Long id;
    @Size(min = 1, max = 50, message = "Code length must be between 1 and 50")
    private String code;

    @Min(value = 0, message = "Minimum number of seats value is 0")
    @Max(value = 500, message = "Maximum number of seats value is 500")
    private Integer numberOfSeats;

    public TrainDto() {}

    public TrainDto(Long id, String code, Integer numberOfSeats) {
        this.id = id;
        this.code = code;
        this.numberOfSeats = numberOfSeats;
    }
}
