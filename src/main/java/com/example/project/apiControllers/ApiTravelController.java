package com.example.project.apiControllers;

import com.example.project.dtos.TravelRequestParamFilter;
import com.example.project.models.Travel;
import com.example.project.services.TravelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Tag(name = "Travel", description = "Endpoints for managing travels")
public class ApiTravelController {
    public final TravelService travelService;
    public final Logger logger;

    @Operation(summary = "Get travels by filters", tags = { "Travel" })
    @GetMapping("public/travel")
    public ResponseEntity<List<Travel>> get(
            @ModelAttribute TravelRequestParamFilter travelRequestParamFilter
    ) {
        try {
            PageRequest request = PageRequest.of(1, 100);
            List<Travel> response = travelService.getAllByCustomParameters(travelRequestParamFilter, request);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
