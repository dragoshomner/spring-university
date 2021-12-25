package com.example.project.services;

import com.example.project.dtos.CreateTrain;
import com.example.project.exceptions.DuplicateEntityException;
import com.example.project.mappers.TrainMapper;
import com.example.project.models.Train;
import com.example.project.repositories.interfaces.ITrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TrainService {
    public final ITrainRepository trainRepository;
    public final TrainMapper trainMapper;

    public Long getCount() {
        return trainRepository.count();
    }

    @Transactional
    public void save(CreateTrain newCreateTrain) {
        if (trainRepository.findByCode(newCreateTrain.getCode()).isPresent()) {
            throw new DuplicateEntityException(Train.class, "code", newCreateTrain.getCode());
        }
        Train newTrain = trainMapper.createTrainToTrain(newCreateTrain);
        trainRepository.save(newTrain);
    }
}
