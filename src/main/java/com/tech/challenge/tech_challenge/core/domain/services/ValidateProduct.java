package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;

import java.util.Objects;

public class ValidateProduct {

    public static void validate(Product product) throws ValidationException {

        validateName(product.getName());

        validatePrice(product.getPrice());

    }

    public static void validateName(String name) throws ValidationException{

        if (Objects.isNull(name) || name.isEmpty()) throw new ValidationException("Invalid product name");

    }

    public static void validatePrice(Double price) throws ValidationException{

        if (price <= 0) throw new ValidationException("Invalid product price");

    }


}
