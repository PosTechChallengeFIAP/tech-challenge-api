package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.tech.challenge.tech_challenge.core.application.exceptions.UsedProductCannotBeDeletedException;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.entities.EProductCategory;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import com.tech.challenge.tech_challenge.core.domain.services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(name = "Product", description = "Endpoint to manage product" )
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/productCategory")
    @Operation(summary = "Finds all product categories", description = "This endpoint is used to find all product categories",
            tags = {"Product"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = EProductCategory.class)))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public List<EProductCategory> all(){
        return Arrays.stream(EProductCategory.values()).toList();
    }

    @GetMapping("/product")
    @Operation(summary = "Finds products", description = "This endpoint is used to find products. " +
            "If the request has a category it returns the products from this category",
            tags = {"Product"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = Product.class)))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public List<Product> all(@RequestParam(required = false) EProductCategory category){
        if(Objects.isNull(category)) return productService.list();
        else return productService.listByCategory(category);
    }

    @GetMapping("/product/{id}")
    @Operation(summary = "Finds product by Id", description = "This endpoint is used to find product by Id",
            tags = {"Product"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(schema = @Schema(implementation = Product.class))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public Product one(@PathVariable UUID id) throws Exception {
        return productService.getById(id);
    }

    @PostMapping("/product")
    @Operation(summary = "Create product", description = "This endpoint is used to create a new product",
            tags = {"Product"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(schema = @Schema(implementation = Product.class))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public Product newProduct(@RequestBody Product product){
        return productService.create(product);
    }

    @PatchMapping("/product/{id}")
    @Operation(summary = "Update Product", description = "This endpoint is used to update product",
            tags = {"Product"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(schema = @Schema(implementation = Product.class))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public Product updateProduct(@PathVariable UUID id, @RequestBody Product product) throws Exception {
        return productService.update(id, product);
    }

    @DeleteMapping("/product/{id}")
    @Operation(summary = "Delete Product", description = "This endpoint is used to Delete product",
            tags = {"Product"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(schema = @Schema(implementation = Product.class))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) throws Exception {
        try {
            productService.delete(id);

            return new ResponseEntity<>("Product successfully deleted.", HttpStatusCode.valueOf(200));
        }catch (UsedProductCannotBeDeletedException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}

