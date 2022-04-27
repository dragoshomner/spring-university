package com.example.project.dtos;

import com.example.project.models.Driver;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class DriverDto {
    @Schema(hidden = true)
    private Long id;
    @Schema(example = "Costica Mihai")
    @Size(min = 3, max = 50, message = "Name length must be between 3 and 50")
    private String name;
    @Schema(example = "10")
    @Min(value = 0, message = "Minimum years of experience value is 0")
    @Max(value = 50, message = "Maximum years of experience value is 50")
    private Integer yearsOfExperience;

    public DriverDto(String name, Integer yearsOfExperience) {
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
    }
}
