package com.example.project.controllers;

import com.example.project.dtos.ResponseMessage;
import com.example.project.dtos.TicketCreate;
import com.example.project.models.Travel;
import com.example.project.models.User;
import com.example.project.services.TicketService;
import com.example.project.services.TravelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Tag(name = "Ticket", description = "Endpoints for managing tickets")
public class TicketController {
    public final TicketService ticketService;
    public final TravelService travelService;
    public final Logger logger;

    @Operation(summary = "Buy a new ticket", tags = "Ticket")
    @PreAuthorize("hasAuthority('CLIENT')")
    @PostMapping("ticket")
    public ResponseEntity<ResponseMessage> buy(
            @RequestBody @Valid TicketCreate newTicket
    ) {
        try {
            User authenticatedUser = (User) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();

            travelService.substractRemainingSeats(newTicket.getTravelId());
            ResponseMessage response = ticketService.save(authenticatedUser.getId(), newTicket);

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            ResponseMessage response = new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @Operation(summary = "Get the tickets of authenticated user", tags = "Ticket")
    @PreAuthorize("hasAuthority('CLIENT')")
    @GetMapping("tickets/my")
    public ResponseEntity<List<Travel>> getMy() {
        try {
            User authenticatedUser = (User) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();
            List<Travel> response = ticketService.getAllByUserId(authenticatedUser.getId());

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
