package com.tech.challenge.tech_challenge.core.application.exceptions;

public class UnableToFetchOrderItems extends RuntimeException {

    public UnableToFetchOrderItems(String orderId){
        super("Unable to fetch order items. orderId = " + orderId);
    }
}
