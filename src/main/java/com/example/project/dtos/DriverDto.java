package com.example.project.dtos;

import com.example.project.models.Driver;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DriverDto {
    @Schema(hidden = true)
    private Long id;
    @Schema(example = "Costica Mihai")
    private String name;
    @Schema(example = "10")
    private Integer yearsOfExperience;

    public DriverDto(String name, Integer yearsOfExperience) {
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
    }
}
