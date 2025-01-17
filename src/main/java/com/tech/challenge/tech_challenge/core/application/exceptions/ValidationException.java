package com.tech.challenge.tech_challenge.core.application.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String message){
        super(message);
    }
}
