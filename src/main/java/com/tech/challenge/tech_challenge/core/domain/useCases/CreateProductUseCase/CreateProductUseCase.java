package com.tech.challenge.tech_challenge.core.domain.useCases.CreateProductUseCase;

import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import com.tech.challenge.tech_challenge.core.domain.repositories.IProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateProductUseCase implements ICreateProductUseCase {
    @Autowired
    private IProductRepository productRepository;

    public Product execute(Product product) {
        product.setActive(true);
        product.validate();

        return productRepository.save(product);
    }
}
