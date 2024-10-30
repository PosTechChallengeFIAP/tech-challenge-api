package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.ProductRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.EProductCategory;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> list(){
        return productRepository.findAll();
    }

    public List<Product> listByCategory(EProductCategory category){
        return productRepository.findProductByCategory(category.ordinal());
    }

    public Product getById(UUID id) throws Exception {
        return productRepository.findById(id).orElseThrow(
            () -> new Exception("Unable to Find Product")
        );
    }

    public Product create(Product product) {
        Error error = product.validate();
        if(!Objects.isNull(error)){
            throw error;
        }

        return productRepository.save(product);
    }
}
