package com.example.project.controllers;

import com.example.project.dtos.ResponseMessage;
import com.example.project.models.Route;
import com.example.project.services.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Tag(name = "Route", description = "Endpoints for managing routes")
public class RouteController {
    public final RouteService routeService;
    public final Logger logger;

    @Operation(summary = "Get all routes", tags = "Route")
    @GetMapping("public/route/all")
    public ResponseEntity<Iterable<Route>> all() {
        try {
            Iterable<Route> response = routeService.getAll();
            return ResponseEntity.ok()
                    .body(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Get a route by its id", tags = "Route")
    @GetMapping("public/route/{id}")
    public ResponseEntity<Route> get(
            @PathVariable @Schema(example = "830") Long id
    ) {
        try {
            Route response = routeService.getOne(id);
            if (response == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Create new route", tags = "Route")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("route")
    public ResponseEntity<ResponseMessage> create(
            @RequestBody @Valid Route route
    ) {
        try {
            ResponseMessage response = routeService.save(route);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ResponseMessage response = new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @Operation(summary = "Delete a route by its id", tags = "Route")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("route/{id}")
    public ResponseEntity delete(
            @PathVariable Long id
    ) {
        try {
            ResponseMessage response = routeService.deleteById(id);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ResponseMessage response = new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
