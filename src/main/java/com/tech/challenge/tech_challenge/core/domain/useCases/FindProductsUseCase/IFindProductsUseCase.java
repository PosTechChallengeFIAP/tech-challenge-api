package com.tech.challenge.tech_challenge.core.domain.useCases.FindProductsUseCase;

import java.util.List;

import com.tech.challenge.tech_challenge.core.domain.entities.Product;

public interface IFindProductsUseCase {
    List<Product> execute();
}
