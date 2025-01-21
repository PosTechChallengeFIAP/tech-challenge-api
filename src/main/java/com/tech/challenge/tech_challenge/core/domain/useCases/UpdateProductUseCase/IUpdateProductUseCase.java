package com.tech.challenge.tech_challenge.core.domain.useCases.UpdateProductUseCase;

import java.util.UUID;

import com.tech.challenge.tech_challenge.core.domain.entities.Product;

public interface IUpdateProductUseCase {
    Product execute(UUID id, Product incompleteProduct) throws IllegalAccessException;
}
