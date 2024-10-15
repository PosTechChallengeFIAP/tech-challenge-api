package com.tech.challenge.tech_challenge.core.application.exceptions;

public class UnableToParseProductCategoryException extends RuntimeException {

    public UnableToParseProductCategoryException(String value){
        super("Unable to parse Product Category. value = " + value);
    }
}
