package com.example.project.services;

import com.example.project.dtos.ResponseMessage;
import com.example.project.models.Travel;
import com.example.project.repositories.interfaces.ITravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TravelService {
    public final ITravelRepository travelRepository;

    public Long getCount() {
        return travelRepository.count();
    }

    public List<Travel> getAllByCustomParameters(Optional<Long> cityFromId,
                                                 Optional<Long> cityToId,
                                                 Optional<LocalDateTime> dateFrom) {
        return travelRepository.findAllByCustomParameters(cityFromId, cityToId, dateFrom);
    }

    @Transactional
    public ResponseMessage save(Travel newTravel) {
        try {
            travelRepository.save(newTravel);
            return new ResponseMessage(HttpStatus.ACCEPTED, "Travel successfully saved!");
        } catch (Exception ex) {
            return new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Travel cannot be saved!");
        }
    }
}
