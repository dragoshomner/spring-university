package com.example.project.services;

import com.example.project.dtos.CreateTrain;
import com.example.project.dtos.TrainView;
import com.example.project.exceptions.DuplicateEntityException;
import com.example.project.mappers.TrainMapper;
import com.example.project.models.Train;
import com.example.project.repositories.interfaces.ITrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainService {
    public final ITrainRepository trainRepository;
    public final TrainMapper trainMapper;

    public Long getCount() {
        return trainRepository.count();
    }

    public List<TrainView> getAll(Integer page, Integer size) {
        Page<Train> trains = trainRepository.findAll(PageRequest.of(page, size));
        return trainMapper.trainIterableToTrainViewList(trains);
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
