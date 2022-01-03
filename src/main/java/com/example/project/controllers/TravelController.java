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
import java.util.Optional;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class TravelController {
    public final TravelService travelService;
    public final Logger logger;

    @GetMapping("public/travel")
    public ResponseEntity<List<Travel>> get(
            @RequestParam Optional<Long> cityFromId,
            @RequestParam Optional<Long> cityToId
    ) {
        try {
            List<Travel> response = travelService.getAllByCityId(cityFromId, cityToId);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
