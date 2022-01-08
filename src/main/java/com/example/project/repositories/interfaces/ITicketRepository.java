package com.example.project.repositories.interfaces;

import com.example.project.models.Ticket;
import com.example.project.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ITicketRepository extends CrudRepository<Ticket, Long> {
    List<Ticket> findAllByUser(User user);
}
