package com.tech.challenge.tech_challenge.core.domain.useCases.FindProductByIdUseCase;

import java.util.UUID;

import com.tech.challenge.tech_challenge.core.domain.entities.Product;

public interface IFindProductByIdUseCase {
    Product execute(UUID id);
}
