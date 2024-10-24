package com.tech.challenge.tech_challenge.core.application.exceptions;

public class UnableToParseQueueStatusException extends RuntimeException {
    public UnableToParseQueueStatusException(String value) {
        super(String.format("Unable to parse queue status. value = %s", value));
    }
}
