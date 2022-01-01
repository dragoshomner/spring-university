package com.example.project.mappers;

import com.example.project.dtos.DriverDto;
import com.example.project.models.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class DriverMapper {
    public abstract Driver driverDtoToDriver(DriverDto driverDto);
    public abstract DriverDto driverToDriverDto(Driver driver);
    public abstract void updateDriverFromDriverDto(DriverDto dto, @MappingTarget Driver driver);

    public List<DriverDto> driverIterableToDriverDtoList(Iterable<Driver> drivers) {
        if (drivers == null) {
            return null;
        }
        List<DriverDto> driverDtoList = new ArrayList<>();
        for (Driver driver : drivers) {
            driverDtoList.add(driverToDriverDto(driver));
        }
        return driverDtoList;
    }
}
