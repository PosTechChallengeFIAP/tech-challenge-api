package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.tech.challenge.tech_challenge.core.application.exceptions.UsedProductCannotBeDeletedException;
import com.tech.challenge.tech_challenge.core.domain.entities.EProductCategory;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import com.tech.challenge.tech_challenge.core.domain.services.ProductService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@Tag(name = "Product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/productCategory")
    public List<EProductCategory> all(){
        return Arrays.stream(EProductCategory.values()).toList();
    }

    @GetMapping("/product")
    public List<Product> all(@RequestParam(required = false) EProductCategory category){
        if(Objects.isNull(category)) return productService.list();
        else return productService.listByCategory(category);
    }

    @GetMapping("/product/{id}")
    public Product one(@PathVariable UUID id) throws Exception {
        return productService.getById(id);
    }

    @PostMapping("/product")
    public Product newProduct(@RequestBody Product product){
        return productService.create(product);
    }

    @PatchMapping("/product/{id}")
    public Product updateProduct(@PathVariable UUID id, @RequestBody Product product) throws Exception {
        return productService.update(id, product);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) throws Exception {
        try {
            productService.delete(id);

            return new ResponseEntity<>("Product successfully deleted.", HttpStatusCode.valueOf(200));
        }catch (UsedProductCannotBeDeletedException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}

