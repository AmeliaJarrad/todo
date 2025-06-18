package com.example.todo.common.exceptions;

import com.example.todo.common.ValidationErrors;

public class ServiceValidationException extends Exception {

    private ValidationErrors errors;

    public ServiceValidationException(ValidationErrors errors) {
        this.errors = errors;
    }
    
    public ValidationErrors getErrors() {
        return this.errors;
    }
}
