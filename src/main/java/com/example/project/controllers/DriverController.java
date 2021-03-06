package com.example.project.controllers;

import com.example.project.dtos.DriverDto;
import com.example.project.dtos.ResponseMessage;
import com.example.project.services.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Tag(name = "Driver", description = "Endpoints for managing drivers")
public class DriverController {
    public final Logger logger;
    public final DriverService driverService;

    @Operation(summary = "Get all paginated drivers", tags = "Driver")
    @GetMapping("public/driver/all")
    public ResponseEntity<List<DriverDto>> all(Pageable pageable) {
        try {
            List<DriverDto> response = driverService.getAll(pageable);
            return ResponseEntity.ok()
                    .body(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Get driver by its id", tags = "Driver")
    @GetMapping("public/driver/{id}")
    public ResponseEntity<DriverDto> get(
            @PathVariable @Schema(example = "245") Long id
    ) {
        try {
            DriverDto response = driverService.getOne(id);
            if (response == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Create new driver", tags = "Driver")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("driver")
    public ResponseEntity<ResponseMessage> create(
            @RequestBody @Valid DriverDto driverDto
    ) {
        try {
            ResponseMessage response = driverService.save(driverDto);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ResponseMessage response = new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @Operation(summary = "Update specific driver", tags = "Driver")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("driver/{id}")
    public ResponseEntity<ResponseMessage> update(
            @PathVariable Long id,
            @RequestBody @Valid DriverDto driverDto
    ) {
        try {
            ResponseMessage response = driverService.update(id, driverDto);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ResponseMessage response = new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @Operation(summary = "Delete a driver by its id", tags = "Driver")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("driver/{id}")
    public ResponseEntity delete(
            @PathVariable Long id
    ) {
        try {
            ResponseMessage response = driverService.deleteById(id);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ResponseMessage response = new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
