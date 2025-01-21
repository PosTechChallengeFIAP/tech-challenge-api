package com.tech.challenge.tech_challenge.core.domain.useCases.FindProductsByCategoryUseCase;

import com.tech.challenge.tech_challenge.core.domain.entities.EProductCategory;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import com.tech.challenge.tech_challenge.core.domain.repositories.IProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindProductsByCategoryUseCase implements IFindProductsByCategoryUseCase {
    @Autowired
    private IProductRepository productRepository;

    public List<Product> execute(EProductCategory category) {
        return productRepository.findProductByCategory(category.ordinal());
    }
}
