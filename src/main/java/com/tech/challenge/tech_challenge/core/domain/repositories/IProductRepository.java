package com.tech.challenge.tech_challenge.core.domain.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.tech.challenge.tech_challenge.core.domain.entities.Product;

public interface IProductRepository {
    Product save(Product product);
    List<Product> findAll();
    List<Product> findProductByCategory(int idCategory);
    Optional<Product> findById(UUID id);
    void delete(Product product);
}
