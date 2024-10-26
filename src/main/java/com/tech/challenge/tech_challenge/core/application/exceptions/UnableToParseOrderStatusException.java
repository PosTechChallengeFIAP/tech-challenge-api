package com.tech.challenge.tech_challenge.core.application.exceptions;

public class UnableToParseOrderStatusException extends RuntimeException {
    public UnableToParseOrderStatusException(String value) {
        super(String.format("Unable to parse order status. value = %s", value));
    }
}
