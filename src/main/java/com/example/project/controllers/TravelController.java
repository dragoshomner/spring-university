package com.example.project.controllers;

import com.example.project.dtos.TrainView;
import com.example.project.models.Travel;
import com.example.project.services.TrainService;
import com.example.project.services.TravelService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class TravelController {
    public final TravelService travelService;
    public final Logger logger;

    @GetMapping("public/travel")
    public ResponseEntity<List<Travel>> get(
            @RequestParam Long cityFromId
    ) {
        try {
            List<Travel> response = travelService.getAllByDepartureCityId(cityFromId);
            if (response == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
