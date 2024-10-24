package com.tech.challenge.tech_challenge.core.application.exceptions;

public class UnableToAddPaymentToOrder extends RuntimeException {
    public UnableToAddPaymentToOrder() {
        super("Unable to add payment to order");
    }
}
