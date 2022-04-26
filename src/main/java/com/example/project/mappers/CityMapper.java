package com.example.project.mappers;

import com.example.project.dtos.CityDto;
import com.example.project.models.City;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CityMapper {
    public abstract City cityDtoToCity(CityDto cityDTO);
    public abstract CityDto cityToCityDto(City city);

    public List<CityDto> cityIterableToCityDtoList(Iterable<City> cities) {
        if (cities == null) {
            return null;
        }
        List<CityDto> cityDtoList = new ArrayList<>();
        for (City city : cities) {
            cityDtoList.add(cityToCityDto(city));
        }
        return cityDtoList;
    }
}
