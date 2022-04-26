package com.example.project.services;

import com.example.project.dtos.*;
import com.example.project.dtos.paging.Paged;
import com.example.project.dtos.paging.Paging;
import com.example.project.exceptions.DuplicateEntityException;
import com.example.project.mappers.TrainMapper;
import com.example.project.models.Train;
import com.example.project.repositories.interfaces.ITrainRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public List<TrainDto> getAll() {
        Iterable<Train> trains = trainRepository.findAll();
        return trainMapper.trainIterableToTrainDtoList(trains);
    }

    public TrainDto getOne(Long id) {
        Train train = trainRepository.findById(id).orElse(null);
        return trainMapper.trainToTrainDto(train);
    }

    public Paged<TrainDto> getAllPaged(int pageNumber, int size, String sortBy) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, sortBy));
        Page<TrainDto> trainDtoPage = trainRepository.findAll(request).map(trainMapper::trainToTrainDto);
        return new Paged<>(trainDtoPage, Paging.of(trainDtoPage.getTotalPages(), pageNumber, size));
    }

    @Transactional
    public ResponseMessage save(TrainDto newCreateTrain) {
        if (trainRepository.findByCode(newCreateTrain.getCode()).isPresent() && newCreateTrain.getId() == null) {
            throw new DuplicateEntityException(Train.class, "code", newCreateTrain.getCode());
        }
        Train newTrain = trainMapper.trainDtoToTrain(newCreateTrain);
        Train savedTrain = trainRepository.save(newTrain);
        TrainDto trainView = trainMapper.trainToTrainDto(savedTrain);
        return new ResponseMessage<TrainDto>(HttpStatus.ACCEPTED, "Train successfully saved!", trainView);
    }

    public ResponseMessage deleteById(Long id) {
        trainRepository.deleteById(id);
        return new ResponseMessage(HttpStatus.ACCEPTED, "Train successfully deleted!");
    }
}
