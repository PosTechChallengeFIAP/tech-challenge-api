package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;

import java.util.Objects;

public class ValidateOrderItem {

    public static void validate(OrderItem orderItem) throws ValidationException {

        if(Objects.isNull(orderItem.getProduct()) || Objects.isNull(orderItem.getProduct().getId())) throw new ValidationException("Invalid Product");

        if(Objects.isNull(orderItem.getQuantity()) || orderItem.getQuantity() < 1) throw new ValidationException("Quantity is lower than 1");

    }
}
