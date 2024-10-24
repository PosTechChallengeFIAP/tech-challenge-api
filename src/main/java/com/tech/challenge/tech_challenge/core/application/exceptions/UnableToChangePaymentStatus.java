package com.tech.challenge.tech_challenge.core.application.exceptions;

public class UnableToChangePaymentStatus extends RuntimeException {
    public UnableToChangePaymentStatus(String oldValue, String value) {
        super(String.format("Unable to change status %s to %s", oldValue ,value));
    }
}
