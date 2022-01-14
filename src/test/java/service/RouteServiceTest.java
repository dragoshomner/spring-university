package service;

import com.example.project.dtos.ResponseMessage;
import com.example.project.models.City;
import com.example.project.models.Route;
import com.example.project.repositories.interfaces.IRouteRepository;
import com.example.project.services.RouteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RouteServiceTest {
    
    @InjectMocks
    private RouteService routeService;

    @Mock
    private IRouteRepository routeRepository;

    private final City cityFrom = new City("Bucuresti");
    private final City cityTo = new City("Iasi");
    private final Integer distance = 200;
    private final Integer duration = 4000;


    @Test
    @DisplayName("Running save route happy flow")
    void saveNewRouteHappyFlow() {
        Route route = new Route(cityFrom, cityTo, distance, duration);
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.ACCEPTED, "Route successfully saved!");

        ResponseMessage responseMessageSave = routeService.save(route);

        assertNotNull(responseMessageSave);
        assertEquals(responseMessageSave.getStatus(), responseMessage.getStatus());
        assertEquals(responseMessageSave.getMessage(), responseMessage.getMessage());
    }

    @Test
    @DisplayName("Running get one route success")
    void getRouteOneSuccess () {
        Route route = new Route(cityFrom, cityTo, distance, duration);


        when(routeRepository.findById(1L)).thenReturn(Optional.of(route));

        Route routeResponse = routeService.getOne(1L);

        assertEquals(routeResponse, route);
    }

    @Test
    @DisplayName("Running get one route not found")
    void getRouteOneNotFound () {
        when(routeRepository.findById(1L)).thenReturn(Optional.empty());

        Route routeResponse = routeService.getOne(1L);

        assertNull(routeResponse);
    }

    @Test
    @DisplayName("Running delete route")
    void deleteRoute () {
        ResponseMessage responseMessage = new ResponseMessage(HttpStatus.ACCEPTED, "Route successfully deleted!");

        ResponseMessage deleteResponse = routeService.deleteById(1L);

        assertEquals(deleteResponse, responseMessage);
    }
}
