package com.tech.challenge.tech_challenge.core.domain.useCases.CreateProductUseCase;

import com.tech.challenge.tech_challenge.core.domain.entities.Product;

public interface ICreateProductUseCase {
    Product execute(Product product);
}
