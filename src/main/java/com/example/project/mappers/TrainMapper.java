package com.example.project.mappers;

import com.example.project.dtos.CreateTrain;
import com.example.project.dtos.TrainEdit;
import com.example.project.dtos.TrainView;
import com.example.project.models.Train;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TrainMapper {
    public abstract Train createTrainToTrain(CreateTrain createTrain);
    public abstract TrainView trainToTrainView(Train train);
    public abstract void updateTrainFromTrainEdit(TrainEdit dto, @MappingTarget Train train);

    public List<TrainView> trainIterableToTrainViewList(Iterable<Train> trains) {
        if (trains == null) {
            return null;
        }
        List<TrainView> trainViewList = new ArrayList<>();
        for (Train train : trains) {
            trainViewList.add(trainToTrainView(train));
        }
        return  trainViewList;
    }
}
