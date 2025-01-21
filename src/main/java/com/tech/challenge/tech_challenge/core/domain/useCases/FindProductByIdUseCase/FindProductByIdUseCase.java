package com.tech.challenge.tech_challenge.core.domain.useCases.FindProductByIdUseCase;

import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import com.tech.challenge.tech_challenge.core.domain.repositories.IProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindProductByIdUseCase implements IFindProductByIdUseCase {
    @Autowired
    private IProductRepository productRepository;

    public Product execute(UUID id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Product.class,String.format("No product with ID %s.", id))
        );
    }
}
