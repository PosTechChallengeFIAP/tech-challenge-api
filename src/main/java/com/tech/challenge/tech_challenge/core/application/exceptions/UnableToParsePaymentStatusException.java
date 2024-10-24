package com.tech.challenge.tech_challenge.core.application.exceptions;

public class UnableToParsePaymentStatusException extends RuntimeException {
    public UnableToParsePaymentStatusException(String value) {
        super(String.format("Unable to parse payment status. value = %s", value));
    }
}
