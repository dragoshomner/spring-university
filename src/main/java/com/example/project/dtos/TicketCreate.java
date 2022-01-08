package com.example.project.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TicketCreate {
    @Schema(example = "100")
    public Long travelId;
}
