package com.example.project.services;

import com.example.project.dtos.ResponseMessage;
import com.example.project.dtos.TravelRequestParamFilter;
import com.example.project.models.Travel;
import com.example.project.repositories.interfaces.ITravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class TravelService {
    public final ITravelRepository travelRepository;

    public Long getCount() {
        return travelRepository.count();
    }

    public List<Travel> getAllByCustomParameters(TravelRequestParamFilter travelRequestParamFilter) {
        return travelRepository.findAllByCustomParameters(
                travelRequestParamFilter.getCityFromId(),
                travelRequestParamFilter.getCityToId(),
                travelRequestParamFilter.getDateFrom()
        );
    }

    public Travel getById(Long id) {
        return travelRepository.findById(id).orElseThrow(
                () -> new RuntimeException(format("Travel with id - %s, not found", id))
        );
    }

    public void substractRemainingSeats(Long travelId) {
        Travel travel = getById(travelId);
        if (travel.getRemainingNumberOfSeats() < 1) {
            throw new RuntimeException("There are not any remaining seat");
        }
        travel.setRemainingNumberOfSeats(travel.getRemainingNumberOfSeats() - 1);
        travelRepository.save(travel);
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
