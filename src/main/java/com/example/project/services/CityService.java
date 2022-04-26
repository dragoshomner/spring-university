package com.example.project.services;

import com.example.project.dtos.CityDto;
import com.example.project.dtos.ResponseMessage;
import com.example.project.dtos.paging.Paged;
import com.example.project.dtos.paging.Paging;
import com.example.project.exceptions.DuplicateEntityException;
import com.example.project.mappers.CityMapper;
import com.example.project.models.City;
import com.example.project.repositories.interfaces.ICityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Paged<CityDto> getAllPaged(int pageNumber, int size, String sortBy) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, sortBy));
        Page<CityDto> cityPage = cityRepository.findAll(request).map(cityMapper::cityToCityDto);
        return new Paged<>(cityPage, Paging.of(cityPage.getTotalPages(), pageNumber, size));
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
            City savedCity = cityRepository.save(newCity);
            return new ResponseMessage<City>(HttpStatus.ACCEPTED, "City successfully saved", savedCity);
        } catch (Exception ex) {
            return new ResponseMessage(HttpStatus.BAD_REQUEST, "Cannot save city");
        }
    }

    public ResponseMessage deleteById(Long id) {
        cityRepository.deleteById(id);
        return new ResponseMessage(HttpStatus.ACCEPTED, "City successfully deleted!");
    }
}
