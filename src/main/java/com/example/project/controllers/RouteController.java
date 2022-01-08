package com.example.project.controllers;

import com.example.project.dtos.DriverDto;
import com.example.project.dtos.ResponseMessage;
import com.example.project.models.Route;
import com.example.project.services.RouteService;
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
public class RouteController {
    public final RouteService routeService;
    public final Logger logger;

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

    @GetMapping("public/route/{id}")
    public ResponseEntity<Route> get(
            @PathVariable Long id
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
