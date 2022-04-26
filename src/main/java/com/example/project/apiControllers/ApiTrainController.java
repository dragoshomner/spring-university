package com.example.project.apiControllers;

import com.example.project.dtos.ResponseMessage;
import com.example.project.dtos.TrainDto;
import com.example.project.services.TrainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;

import javax.validation.Valid;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Tag(name = "Train", description = "Endpoints for managing trains")
public class ApiTrainController {
    public final TrainService trainService;
    public final Logger logger;

    @Operation(summary = "Get all paginated trains", tags = { "Train" })
    @GetMapping("public/train/all")
    public ResponseEntity<List<TrainDto>> all(Pageable pageable) {
        try {
            List<TrainDto> response = trainService.getAll();
            return ResponseEntity.ok()
                    .body(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Get a train by its id", tags = "Train")
    @GetMapping("public/train/{id}")
    public ResponseEntity<TrainDto> get(
            @PathVariable @Schema(example = "100") Long id
    ) {
        try {
            TrainDto response = trainService.getOne(id);
            if (response == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Delete a train by its id", tags = "Train")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("train/{id}")
    public ResponseEntity delete(
            @PathVariable Long id
    ) {
        try {
            ResponseMessage response = trainService.deleteById(id);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ResponseMessage response = new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
