package service;

import com.example.project.dtos.CityDto;
import com.example.project.dtos.ResponseMessage;
import com.example.project.mappers.CityMapper;
import com.example.project.mappers.CityMapperImpl;
import com.example.project.models.City;
import com.example.project.repositories.interfaces.ICityRepository;
import com.example.project.services.CityService;
import org.apache.coyote.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @InjectMocks
    private CityService cityService;

    @Mock
    private ICityRepository cityRepository;
    @Mock
    private CityMapper cityMapper;

    @Test
    @DisplayName("Running save city happy flow")
    void saveNewCityHappyFlow() {
        CityDto cityDto = new CityDto("New York");
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.ACCEPTED, "City successfully saved");

        when(cityRepository.findByName(cityDto.getName())).thenReturn(Optional.empty());

        ResponseMessage responseMessageSave = cityService.save(cityDto);

        assertNotNull(responseMessageSave);
        assertEquals(responseMessageSave.getStatus(), responseMessage.getStatus());
        assertEquals(responseMessageSave.getMessage(), responseMessage.getMessage());
    }

    @Test
    @DisplayName("Running save city return duplicate exception")
    void saveNewCityDuplicateException() {
        CityDto cityDto = new CityDto("New York");
        Optional<City> duplicateCity = Optional.of(new City());
        String exceptionMessage = "City with name New York already exists";

        when(cityRepository.findByName(cityDto.getName())).thenReturn(duplicateCity);

        RuntimeException runtimeException = assertThrows(
                RuntimeException.class,
                () -> cityService.save(cityDto));

        assertEquals(exceptionMessage, runtimeException.getMessage());
    }

    @Test
    @DisplayName("Running get one city success")
    void getCityOneSuccess () {
        String cityName = "New York";
        City city = new City(cityName);

        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(cityMapper.cityToCityDto(city)).thenReturn(new CityDto(cityName));

        CityDto cityDtoResponse = cityService.getOne(1L);

        assertEquals(cityDtoResponse.getName(), cityName);
    }

    @Test
    @DisplayName("Running get one city not found")
    void getCityOneNotFound () {
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());
        when(cityMapper.cityToCityDto(null)).thenReturn(null);

        CityDto cityDtoResponse = cityService.getOne(1L);

        assertNull(cityDtoResponse);
    }

    @Test
    @DisplayName("Running delete city")
    void deleteCity () {
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.ACCEPTED, "City successfully deleted!");

        ResponseMessage deleteResponse = cityService.deleteById(1L);

        assertEquals(deleteResponse, responseMessage);
    }
}
