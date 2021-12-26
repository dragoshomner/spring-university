package com.example.project.services;

import com.example.project.dtos.CreateTrain;
import com.example.project.dtos.ResponseMessage;
import com.example.project.dtos.TrainEdit;
import com.example.project.dtos.TrainView;
import com.example.project.exceptions.DuplicateEntityException;
import com.example.project.mappers.TrainMapper;
import com.example.project.models.Train;
import com.example.project.repositories.interfaces.ITrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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

    public TrainView getOne(Long id) {
        Train train = trainRepository.findById(id).orElse(null);
        return trainMapper.trainToTrainView(train);
    }

    @Transactional
    public void save(CreateTrain newCreateTrain) {
        if (trainRepository.findByCode(newCreateTrain.getCode()).isPresent()) {
            throw new DuplicateEntityException(Train.class, "code", newCreateTrain.getCode());
        }
        Train newTrain = trainMapper.createTrainToTrain(newCreateTrain);
        trainRepository.save(newTrain);
    }

    @Transactional
    public ResponseMessage update(Long id, TrainEdit trainEdit) {
        Train train = trainRepository.findById(id).orElse(null);
        if (train != null) {
            trainMapper.updateTrainFromTrainEdit(trainEdit, train);
            trainRepository.save(train);
            return new ResponseMessage(HttpStatus.ACCEPTED, "Train successfully updated!");
        }
        return new ResponseMessage(HttpStatus.NOT_FOUND, "Train not found!");
    }

    public ResponseMessage deleteById(Long id) {
        trainRepository.deleteById(id);
        return new ResponseMessage(HttpStatus.ACCEPTED, "Train successfully deleted!");
    }
}
