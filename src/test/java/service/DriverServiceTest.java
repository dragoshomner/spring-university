package service;

import com.example.project.dtos.DriverDto;
import com.example.project.dtos.ResponseMessage;
import com.example.project.mappers.DriverMapper;
import com.example.project.models.Driver;
import com.example.project.repositories.interfaces.IDriverRepository;
import com.example.project.services.DriverService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DriverServiceTest {
    @InjectMocks
    private DriverService driverService;

    @Mock
    private IDriverRepository driverRepository;
    @Mock
    private DriverMapper driverMapper;

    private final String driverName = "Vasile Ion";
    private final Integer yearsOfExperience = 20;
    private final Long id = 1L;

    @Test
    @DisplayName("Running save driver happy flow")
    void saveNewDriverHappyFlow() {
        DriverDto driverDto = new DriverDto(driverName, yearsOfExperience);
        Driver driver = new Driver(driverName, yearsOfExperience);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.ACCEPTED, "Driver successfully saved!");

        when(driverMapper.driverDtoToDriver(driverDto)).thenReturn(driver);

        ResponseMessage responseMessageSave = driverService.save(driverDto);

        assertNotNull(responseMessageSave);
        assertEquals(responseMessageSave, responseMessage);
    }

    @Test
    @DisplayName("Running update driver happy flow")
    void updateDriverHappyFlow() {
        DriverDto driverDto = new DriverDto(driverName, yearsOfExperience);
        Driver driver = new Driver(driverName, yearsOfExperience);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.ACCEPTED, "Driver successfully updated!");

        when(driverRepository.findById(id)).thenReturn(Optional.of(driver));

        ResponseMessage responseMessageSave = driverService.update(id, driverDto);

        assertNotNull(responseMessageSave);
        assertEquals(responseMessageSave, responseMessage);
    }

    @Test
    @DisplayName("Running update driver not found")
    void updateDriverNotFound() {
        DriverDto driverDto = new DriverDto(driverName, yearsOfExperience);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND, "Driver not found!");

        when(driverRepository.findById(id)).thenReturn(Optional.empty());

        ResponseMessage responseMessageSave = driverService.update(id, driverDto);

        assertNotNull(responseMessageSave);
        assertEquals(responseMessageSave, responseMessage);
    }

    @Test
    @DisplayName("Running get one driver success")
    void getDriverOneSuccess () {
        Driver driver = new Driver(driverName, yearsOfExperience);

        when(driverRepository.findById(id)).thenReturn(Optional.of(driver));
        when(driverMapper.driverToDriverDto(driver)).thenReturn(new DriverDto(driverName, yearsOfExperience));

        DriverDto driverDtoResponse = driverService.getOne(id);

        assertEquals(driverDtoResponse.getName(), driverName);
    }

    @Test
    @DisplayName("Running get one driver not found")
    void getCityOneNotFound () {
        when(driverRepository.findById(id)).thenReturn(Optional.empty());
        when(driverMapper.driverToDriverDto(null)).thenReturn(null);

        DriverDto driverDtoResponse = driverService.getOne(id);

        assertNull(driverDtoResponse);
    }

    @Test
    @DisplayName("Running delete city")
    void deleteCity () {
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.ACCEPTED, "Driver successfully deleted!");

        ResponseMessage deleteResponse = driverService.deleteById(id);

        assertEquals(deleteResponse, responseMessage);
    }
}
