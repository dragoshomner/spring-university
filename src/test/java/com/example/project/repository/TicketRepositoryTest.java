package com.example.project.repository;

import com.example.project.models.Ticket;
import com.example.project.repositories.interfaces.ITicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("mysql")
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class TicketRepositoryTest {

    @Autowired
    private ITicketRepository ticketRepository;

    @Test
    @Order(2)
    public void findAll() {
        List<Ticket> tickets = (List<Ticket>) ticketRepository.findAll();
        assertFalse(tickets.isEmpty());
        log.info("findAll ...");
        tickets.forEach(ticket -> log.info(ticket.getId().toString()));
    }

    @Test
    @Order(3)
    public void findById() {
        Optional<Ticket> ticket = ticketRepository.findById(390L);
        assertTrue(ticket.isPresent());
        log.info("findById ...");
        ticket.ifPresent(ticket1 ->  log.info(ticket1.getId().toString()));
    }
}
