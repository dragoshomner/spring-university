package com.example.project.dtos;

import lombok.Data;

@Data
public class CityDto {
    private Long id;
    private String name;

    public CityDto(String name) {
        this.name = name;
    }
}
