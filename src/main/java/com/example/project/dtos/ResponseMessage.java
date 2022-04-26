package com.example.project.dtos;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Data
public class ResponseMessage<T> {
    private HttpStatus status;
    private String message;
    private T model;

    public ResponseMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseMessage(HttpStatus status, String message, T model) {
        this.status = status;
        this.message = message;
        this.model = model;
    }
}
