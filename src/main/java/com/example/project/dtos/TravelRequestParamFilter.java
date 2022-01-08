package com.example.project.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
public class TravelRequestParamFilter {
    @Schema(example = "574")
    Optional<Long> cityFromId;
    @Schema(example = "573")
    Optional<Long> cityToId;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    @Schema(example = "2022-02-01T08:35:35")
    Optional<LocalDateTime> dateFrom;
}