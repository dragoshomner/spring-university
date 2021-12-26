package com.example.project.dtos;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseMessage {
    private HttpStatus status;
    private String message;

    public ResponseMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
