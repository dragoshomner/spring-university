package com.example.project.exceptions;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException(String message) {
        super(message);
    }

    public NotFoundUserException(Class<?> myClass, Long id) {
        super(String.format("Entity %s with id %d not found", myClass.getSimpleName(), id));
    }
}
