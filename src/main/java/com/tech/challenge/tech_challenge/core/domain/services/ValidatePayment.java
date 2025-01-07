package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import java.util.Objects;

public class ValidatePayment {

    public static void validate(Double value) throws ValidationException {

        if (Objects.isNull(value) || value == 0) throw new ValidationException("Invalid payment value");

    }
}
