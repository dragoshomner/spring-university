package com.example.project.dtos;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class CityDto {
    private Long id;

    @Size(min = 3, max = 50, message = "Name length must be between 3 and 50")
    private String name;

    public CityDto(String name) {
        this.name = name;
    }
}
