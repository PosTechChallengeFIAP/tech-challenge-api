package com.tech.challenge.tech_challenge.core.application.exceptions;

public class InvalidEmailAddress extends RuntimeException {
    public InvalidEmailAddress(String email) {
        super(String.format("'%s' is an invalid email. Please follow example: example@example.com", email));
    }
}
