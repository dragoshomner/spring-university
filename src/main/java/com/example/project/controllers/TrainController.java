package com.example.project.controllers;

import com.example.project.dtos.ResponseMessage;
import com.example.project.dtos.TrainEdit;
import com.example.project.dtos.TrainView;
import com.example.project.models.Role;
import com.example.project.services.TrainService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class TrainController {
    public final TrainService trainService;
    public final Logger logger;

    @GetMapping("public/train/all")
    public ResponseEntity<List<TrainView>> all(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        try {
            List<TrainView> response = trainService.getAll(page, size);
            return ResponseEntity.ok()
                    .body(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("public/train/{id}")
    public ResponseEntity<TrainView> get(
            @PathVariable Long id
    ) {
        try {
            TrainView response = trainService.getOne(id);
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
    @PutMapping("train/{id}")
    public ResponseEntity<ResponseMessage> update(
            @PathVariable Long id,
            @RequestBody @Valid TrainEdit trainEdit
    ) {
        try {
            ResponseMessage response = trainService.update(id, trainEdit);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ResponseMessage response = new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

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
