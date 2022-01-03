package com.example.project.services;

import com.example.project.dtos.DriverDto;
import com.example.project.dtos.ResponseMessage;
import com.example.project.models.Driver;
import com.example.project.models.Route;
import com.example.project.repositories.interfaces.IDriverRepository;
import com.example.project.repositories.interfaces.IRouteRepository;
import lombok.RequiredArgsConstructor;
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

    public Iterable<Route> getAll() {
        return routeRepository.findAll();
    }

    @Transactional
    public ResponseMessage save(Route newRoute) {
        try {
            routeRepository.save(newRoute);
            return new ResponseMessage(HttpStatus.ACCEPTED, "Route successfully saved!");
        } catch (Exception ex) {
            return new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Route cannot be saved!");
        }
    }
}
