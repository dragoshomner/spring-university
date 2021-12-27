package com.example.project.controllers;

import com.example.project.dtos.CityDto;
import com.example.project.dtos.ResponseMessage;
import com.example.project.services.CityService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class CityController {
    public final CityService cityService;
    public final Logger logger;

    @GetMapping("public/city/all")
    public ResponseEntity<List<CityDto>> all() {
        try {
            List<CityDto> response = cityService.getAll();
            return ResponseEntity.ok()
                    .body(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("public/city/{id}")
    public ResponseEntity<CityDto> get(
            @PathVariable Long id
    ) {
        try {
            CityDto response = cityService.getOne(id);
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
    @DeleteMapping("city/{id}")
    public ResponseEntity delete(
            @PathVariable Long id
    ) {
        try {
            ResponseMessage response = cityService.deleteById(id);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ResponseMessage response = new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
