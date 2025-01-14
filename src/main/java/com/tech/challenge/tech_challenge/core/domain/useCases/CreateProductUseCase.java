package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.ProductRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateProductUseCase {
    @Autowired
    private ProductRepository productRepository;

    public Product execute(Product product) throws ValidationException {
        product.validate();

        return productRepository.save(product);
    }
}
