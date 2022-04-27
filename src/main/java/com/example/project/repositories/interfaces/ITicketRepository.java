package com.example.project.repositories.interfaces;

import com.example.project.models.Ticket;
import com.example.project.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ITicketRepository extends PagingAndSortingRepository<Ticket, Long> {
    Page<Ticket> findAllByUser(User user, Pageable pageable);
}
