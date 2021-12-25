package com.example.project.controllers;

import com.example.project.dtos.TrainView;
import com.example.project.services.TrainService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class TrainController {
    public final TrainService trainService;

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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
