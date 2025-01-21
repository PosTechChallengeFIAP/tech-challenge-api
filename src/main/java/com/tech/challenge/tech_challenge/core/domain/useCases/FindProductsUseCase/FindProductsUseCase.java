package com.tech.challenge.tech_challenge.core.domain.useCases.FindProductsUseCase;

import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import com.tech.challenge.tech_challenge.core.domain.repositories.IProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindProductsUseCase implements IFindProductsUseCase {
    @Autowired
    private IProductRepository productRepository;

    public List<Product> execute() {
        return productRepository.findAll();
    }

}
