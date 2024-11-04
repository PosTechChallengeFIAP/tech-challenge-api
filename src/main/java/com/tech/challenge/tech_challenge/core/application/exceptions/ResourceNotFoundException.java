package com.tech.challenge.tech_challenge.core.application.exceptions;

public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(Class resourceClass) {
        super(String.format("%s not found.", resourceClass.getSimpleName()));
    }

    public ResourceNotFoundException(Class resourceClass, String info) {
        super(String.format("%s not found. %s", resourceClass.getSimpleName(), info));
    }
}
