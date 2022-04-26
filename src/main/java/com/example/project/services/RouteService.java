package com.example.project.services;

import com.example.project.dtos.ResponseMessage;
import com.example.project.dtos.paging.Paged;
import com.example.project.dtos.paging.Paging;
import com.example.project.models.Route;
import com.example.project.repositories.interfaces.IRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {
    public final IRouteRepository routeRepository;

    public Long getCount() {
        return routeRepository.count();
    }

    public List<Route> getAll() {
        return routeRepository.findAll();
    }

    public Paged<Route> getAllPaged(int pageNumber, int size, String sortBy) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, sortBy));
        Page<Route> routePage = routeRepository.findAll(request);
        return new Paged<>(routePage, Paging.of(routePage.getTotalPages(), pageNumber, size));
    }

    public Route getOne(Long id) {
        Route route = routeRepository.findById(id).orElse(null);
        return route;
    }

    @Transactional
    public ResponseMessage save(Route newRoute) {
        try {
            Route savedRoute = routeRepository.save(newRoute);
            return new ResponseMessage<Route>(HttpStatus.ACCEPTED, "Route successfully saved!", savedRoute);
        } catch (Exception ex) {
            return new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Route cannot be saved!");
        }
    }

    public ResponseMessage deleteById(Long id) {
        routeRepository.deleteById(id);
        return new ResponseMessage(HttpStatus.ACCEPTED, "Route successfully deleted!");
    }
}
