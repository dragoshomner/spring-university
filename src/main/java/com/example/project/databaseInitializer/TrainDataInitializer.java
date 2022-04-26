package com.example.project.databaseInitializer;

import com.example.project.dtos.TrainDto;
import com.example.project.exceptions.DuplicateEntityException;
import com.example.project.models.Train;
import com.example.project.services.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;

@Component
@RequiredArgsConstructor
public class TrainDataInitializer implements IDataInitializer {
    public final TrainService trainService;
    public final Logger logger;

    public void initialize() {
        try {
            if (trainService.getCount() > 0) {
                throw new DuplicateEntityException(Train.class);
            }
            for (int i = 1; i < 100; i++) {
                TrainDto createTrain = new TrainDto();
                createTrain.setCode(createCodeFromNumber(i));
                createTrain.setNumberOfSeats(generateRandomSeatNumber());
                trainService.save(createTrain);
            }
        } catch (Exception e) {
            logger.info("[TrainDataInitializer] " + e.getMessage());
        }
    }

    private String createCodeFromNumber(Integer codeNumber) {
        return "IR" + "0".repeat((3 - String.valueOf(codeNumber).length())) + codeNumber;
    }

    private Integer generateRandomSeatNumber() {
        Integer minSeatNumber = 10, maxSeatNumber = 100;
        return (int) ((Math.random() * (maxSeatNumber - minSeatNumber)) + minSeatNumber);
    }
}
