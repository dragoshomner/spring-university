package com.example.project.mappers;

import com.example.project.dtos.CreateTrain;
import com.example.project.models.Train;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class TrainMapper {
    public abstract Train createTrainToTrain(CreateTrain createTrain);
}
