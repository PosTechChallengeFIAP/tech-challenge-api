package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import com.tech.challenge.tech_challenge.core.domain.repositories.IProductRepository;
import com.tech.challenge.tech_challenge.core.domain.services.generic.Patcher;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindProductByIdUseCase.FindProductByIdUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateProductUseCase {
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private FindProductByIdUseCase findProductByIdUseCase;

    @Autowired
    private Patcher<Product> productPatcher;

    public Product execute(UUID id, Product incompleteProduct) throws ResourceNotFoundException, ValidationException, IllegalAccessException {
        Product product = findProductByIdUseCase.execute(id);
        Product updatedProduct = productPatcher.execute(product, incompleteProduct);
        updatedProduct.validate();

        return productRepository.save(updatedProduct);
    }
}
