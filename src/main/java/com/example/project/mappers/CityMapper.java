package com.example.project.mappers;

import com.example.project.dtos.CityDto;
import com.example.project.dtos.TrainEdit;
import com.example.project.dtos.TrainView;
import com.example.project.models.City;
import com.example.project.models.Train;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

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
