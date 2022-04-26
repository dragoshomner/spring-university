package com.example.project.services;

import com.example.project.dtos.ResponseMessage;
import com.example.project.dtos.TrainDto;
import com.example.project.dtos.TravelRequestParamFilter;
import com.example.project.dtos.paging.Paged;
import com.example.project.dtos.paging.Paging;
import com.example.project.models.Travel;
import com.example.project.repositories.interfaces.ITravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class TravelService {
    public final ITravelRepository travelRepository;

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

    public List<Travel> getAll() {
        return travelRepository.findAll();
    }

    public Paged<Travel> getAllPaged(int pageNumber, int size, String sortBy) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, sortBy));
        Page<Travel> travelPage = travelRepository.findAll(request);
        return new Paged<>(travelPage, Paging.of(travelPage.getTotalPages(), pageNumber, size));
    }

    public void subtractRemainingSeats(Long travelId) {
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
            Travel savedTravel = travelRepository.save(newTravel);
            return new ResponseMessage<Travel>(HttpStatus.ACCEPTED, "Travel successfully saved!", savedTravel);
        } catch (Exception ex) {
            return new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Travel cannot be saved!");
        }
    }

    public ResponseMessage deleteById(Long id) {
        travelRepository.deleteById(id);
        return new ResponseMessage(HttpStatus.ACCEPTED, "Travel successfully deleted!");
    }
}
