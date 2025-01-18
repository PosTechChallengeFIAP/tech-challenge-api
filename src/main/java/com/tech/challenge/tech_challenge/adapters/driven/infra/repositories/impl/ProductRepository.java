package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.impl;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa.ProductRepositoryJPA;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import com.tech.challenge.tech_challenge.core.domain.repositories.IProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository implements IProductRepository {
    @Autowired
    ProductRepositoryJPA productRepositoryJPA;

    @Override
    public Product save(Product productToSave) {
        return productRepositoryJPA.save(productToSave);
    }

    @Override
    public List<Product> findAll() {
        return productRepositoryJPA.findAll();
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productRepositoryJPA.findById(id);
    }

    @Override
    public List<Product> findProductByCategory(int idCategory) {
        return productRepositoryJPA.findProductByCategory(idCategory);
    }

    @Override
    public void delete(Product product) {
        productRepositoryJPA.delete(product);
    }
}
