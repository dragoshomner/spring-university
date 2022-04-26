package com.example.project.dtos;

import com.example.project.models.Travel;
import lombok.Data;

@Data
public class TicketCreateRequest {
    public Long id;
    public Long user;
    public Travel travel;
}
