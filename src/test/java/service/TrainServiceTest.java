package service;

import com.example.project.dtos.ResponseMessage;
import com.example.project.dtos.TrainDto;
import com.example.project.mappers.TrainMapper;
import com.example.project.models.Train;
import com.example.project.repositories.interfaces.ITrainRepository;
import com.example.project.services.TrainService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainServiceTest {
    @InjectMocks
    private TrainService trainService;

    @Mock
    private ITrainRepository trainRepository;
    @Mock
    private TrainMapper trainMapper;

    private final String trainCode = "ABC";
    private final Integer numberOfSeats = 100;
    private final Long id = 1L;

    @Test
    @DisplayName("Running save train happy flow")
    void saveNewTrainHappyFlow() {
        TrainDto createTrain = new TrainDto(id, trainCode, numberOfSeats);
        Train train = new Train(trainCode, numberOfSeats);

        when(trainMapper.trainDtoToTrain(createTrain)).thenReturn(train);

        assertDoesNotThrow(() -> trainService.save(createTrain));
    }

    @Test
    @DisplayName("Running get one train success")
    void getTrainOneSuccess () {
        Train train = new Train(trainCode, numberOfSeats);

        when(trainRepository.findById(id)).thenReturn(Optional.of(train));
        when(trainMapper.trainToTrainDto(train)).thenReturn(new TrainDto(id, trainCode, numberOfSeats));

        TrainDto trainViewResponse = trainService.getOne(id);

        assertEquals(trainViewResponse.getCode(), trainCode);
    }

    @Test
    @DisplayName("Running get one train not found")
    void getCityOneNotFound () {
        when(trainRepository.findById(id)).thenReturn(Optional.empty());
        when(trainMapper.trainToTrainDto(null)).thenReturn(null);

        TrainDto trainViewResponse = trainService.getOne(id);

        assertNull(trainViewResponse);
    }

    @Test
    @DisplayName("Running delete city")
    void deleteCity () {
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.ACCEPTED, "Train successfully deleted!");

        ResponseMessage deleteResponse = trainService.deleteById(id);

        assertEquals(deleteResponse, responseMessage);
    }
}
