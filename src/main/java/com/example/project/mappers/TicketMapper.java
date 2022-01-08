package com.example.project.mappers;

import com.example.project.dtos.TicketCreate;
import com.example.project.models.Ticket;
import com.example.project.models.Travel;
import com.example.project.models.User;
import com.example.project.services.TravelService;
import com.example.project.services.UserService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class TicketMapper {
    @Autowired
    protected UserService userService;
    @Autowired
    protected TravelService travelService;

    public Ticket ticketCreateWithUserIdToTicket(Long userId, TicketCreate ticketCreate) {
        if (ticketCreate == null) {
            return null;
        }
        Ticket ticket = new Ticket();
        ticket.setUser(new User(userId));
        ticket.setTravel(new Travel(ticketCreate.getTravelId()));
        return ticket;
    }
}
