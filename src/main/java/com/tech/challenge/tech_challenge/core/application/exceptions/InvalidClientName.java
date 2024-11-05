package com.tech.challenge.tech_challenge.core.application.exceptions;

public class InvalidClientName extends ValidationException {
    public InvalidClientName() {
        super("Name should be not empty");
    }
}
