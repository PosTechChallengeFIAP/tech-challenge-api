package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import com.tech.challenge.tech_challenge.core.domain.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product")
    public List<Product> all(){
        return productService.list();
    }

    @GetMapping("/product/{id}")
    public Product one(@PathVariable UUID id) throws Exception {
        return productService.getById(id);
    }

    @PostMapping("/product")
    public Product newProduct(@RequestBody Product product){
        return productService.create(product);
    }
}

