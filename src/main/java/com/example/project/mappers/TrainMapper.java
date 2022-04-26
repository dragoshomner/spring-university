package com.example.project.mappers;

import com.example.project.dtos.TrainDto;
import com.example.project.models.Train;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TrainMapper {
    public abstract Train trainDtoToTrain(TrainDto trainDto);
    public abstract TrainDto trainToTrainDto(Train train);

    public List<TrainDto> trainIterableToTrainDtoList(Iterable<Train> trains) {
        if (trains == null) {
            return null;
        }
        List<TrainDto> trainDtoList = new ArrayList<>();
        for (Train train : trains) {
            trainDtoList.add(trainToTrainDto(train));
        }
        return trainDtoList;
    }
}
