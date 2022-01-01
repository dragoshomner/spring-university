package com.example.project.services;

import com.example.project.dtos.*;
import com.example.project.mappers.DriverMapper;
import com.example.project.models.Driver;
import com.example.project.repositories.interfaces.IDriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverService {
    public final IDriverRepository driverRepository;
    public final DriverMapper driverMapper;

    public Long getCount() {
        return driverRepository.count();
    }

    public List<DriverDto> getAll(Integer page, Integer size) {
        Page<Driver> drivers = driverRepository.findAll(PageRequest.of(page, size));
        return driverMapper.driverIterableToDriverDtoList(drivers);
    }

    public DriverDto getOne(Long id) {
        Driver driver = driverRepository.findById(id).orElse(null);
        return driverMapper.driverToDriverDto(driver);
    }

    @Transactional
    public ResponseMessage save(DriverDto newDriverDto) {
        try {
            Driver newDriver = driverMapper.driverDtoToDriver(newDriverDto);
            driverRepository.save(newDriver);
            return new ResponseMessage(HttpStatus.ACCEPTED, "Driver successfully saved!");
        } catch (Exception ex) {
            return new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Driver cannot be saved!");
        }
    }

    @Transactional
    public ResponseMessage update(Long id, DriverDto driverDto) {
        Driver driver = driverRepository.findById(id).orElse(null);
        if (driver != null) {
            driverDto.setId(id);
            driverDto.setName(driverDto.getName() == null ? driver.getName() : driverDto.getName());
            driverDto.setYearsOfExperience(driverDto.getYearsOfExperience() == null ?
                    driver.getYearsOfExperience() : driverDto.getYearsOfExperience());
            driverMapper.updateDriverFromDriverDto(driverDto, driver);
            driverRepository.save(driver);
            return new ResponseMessage(HttpStatus.ACCEPTED, "Driver successfully updated!");
        }
        return new ResponseMessage(HttpStatus.NOT_FOUND, "Driver not found!");
    }

    public ResponseMessage deleteById(Long id) {
        driverRepository.deleteById(id);
        return new ResponseMessage(HttpStatus.ACCEPTED, "Driver successfully deleted!");
    }
}
