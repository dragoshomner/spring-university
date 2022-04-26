package com.example.project.services;

import com.example.project.dtos.ResponseMessage;
import com.example.project.dtos.TicketCreate;
import com.example.project.dtos.paging.Paged;
import com.example.project.dtos.paging.Paging;
import com.example.project.mappers.TicketMapper;
import com.example.project.models.Ticket;
import com.example.project.models.Travel;
import com.example.project.models.User;
import com.example.project.repositories.interfaces.ITicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Ticket getOne(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }
    
    public List<Travel> getAllByUserId(Long userId) {
        var travels = ticketRepository.findAllByUser(new User(userId));
        return travels.stream().map(Ticket::getTravel).collect(Collectors.toList());
    }

    public Paged<Ticket> getAllPaged(int pageNumber, int size, String sortBy) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.ASC, sortBy));
        Page<Ticket> ticketPage = ticketRepository.findAll(request);
        return new Paged<>(ticketPage, Paging.of(ticketPage.getTotalPages(), pageNumber, size));
    }

    @Transactional
    public ResponseMessage save(Ticket newTicket) {
        try {
            Ticket savedTicket = ticketRepository.save(newTicket);
            return new ResponseMessage<Ticket>(HttpStatus.ACCEPTED, "Ticket successfully saved!", savedTicket);
        } catch (Exception ex) {
            return new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Ticket cannot be saved!");
        }
    }

    public ResponseMessage deleteById(Long id) {
        ticketRepository.deleteById(id);
        return new ResponseMessage(HttpStatus.ACCEPTED, "Ticket successfully deleted!");
    }
}
