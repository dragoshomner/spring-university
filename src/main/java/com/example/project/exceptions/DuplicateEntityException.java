package com.example.project.exceptions;

import javax.validation.ValidationException;

public class DuplicateEntityException extends ValidationException {
    public DuplicateEntityException(String error) {
        super(error);
    }

    public DuplicateEntityException(Class<?> classParameter) {
        super(String.format("%s already exists", classParameter.getSimpleName()));
    }

    public DuplicateEntityException(Class<?> classParameter, Integer id) {
        super(String.format("%s with id %d already exists", classParameter.getSimpleName(), id));
    }

    public DuplicateEntityException(Class<?> classParameter, String keyName, String keyValue) {
        super(String.format("%s with %s %d already exists", classParameter.getSimpleName(), keyName, keyValue));
    }
}
