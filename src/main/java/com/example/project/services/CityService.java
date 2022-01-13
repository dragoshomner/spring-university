package com.example.project.services;

import com.example.project.dtos.CityDto;
import com.example.project.dtos.ResponseMessage;
import com.example.project.dtos.TrainEdit;
import com.example.project.dtos.TrainView;
import com.example.project.exceptions.DuplicateEntityException;
import com.example.project.mappers.CityMapper;
import com.example.project.models.City;
import com.example.project.models.Train;
import com.example.project.repositories.interfaces.ICityRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    public final ICityRepository cityRepository;
    public final CityMapper cityMapper;

    public Long getCount() {
        return cityRepository.count();
    }

    public List<CityDto> getAll() {
        Iterable<City> cities = cityRepository.findAll();
        return cityMapper.cityIterableToCityDtoList(cities);
    }

    public CityDto getOne(Long id) {
        City city = cityRepository.findById(id).orElse(null);
        return cityMapper.cityToCityDto(city);
    }

    @Transactional
    public ResponseMessage save(CityDto cityDto) {
        if (cityRepository.findByName(cityDto.getName()).isPresent()) {
            throw new DuplicateEntityException(City.class, "name", cityDto.getName());
        }
        City newCity = cityMapper.cityDtoToCity(cityDto);
        try {
            cityRepository.save(newCity);
            return new ResponseMessage(HttpStatus.ACCEPTED, "City successfully saved");
        } catch (Exception ex) {
            return new ResponseMessage(HttpStatus.BAD_REQUEST, "Cannot save city");
        }
    }

    public ResponseMessage deleteById(Long id) {
        cityRepository.deleteById(id);
        return new ResponseMessage(HttpStatus.ACCEPTED, "City successfully deleted!");
    }
}
