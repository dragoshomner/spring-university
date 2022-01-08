package com.example.project.services;

import com.example.project.dtos.ResponseMessage;
import com.example.project.dtos.TicketCreate;
import com.example.project.mappers.TicketMapper;
import com.example.project.models.Ticket;
import com.example.project.models.Travel;
import com.example.project.models.User;
import com.example.project.repositories.interfaces.ITicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    public final ITicketRepository ticketRepository;
    public final TicketMapper ticketMapper;

    public List<Travel> getAllByUserId(Long userId) {
        var travels = ticketRepository.findAllByUser(new User(userId));
        return travels.stream().map(Ticket::getTravel).collect(Collectors.toList());
    }

    @Transactional
    public ResponseMessage save(Long userId, TicketCreate ticketCreate) {
        try {
            Ticket newTicket = ticketMapper.ticketCreateWithUserIdToTicket(userId, ticketCreate);
            ticketRepository.save(newTicket);
            return new ResponseMessage(HttpStatus.ACCEPTED, "Ticket successfully saved!");
        } catch (Exception ex) {
            return new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Ticket cannot be saved!");
        }
    }
}
