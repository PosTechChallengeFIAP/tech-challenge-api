package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.UsedProductCannotBeDeletedException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.application.message.MessageResponse;
import com.tech.challenge.tech_challenge.core.domain.entities.*;
import com.tech.challenge.tech_challenge.core.domain.useCases.*;
import com.tech.challenge.tech_challenge.core.domain.useCases.CreateProductUseCase.CreateProductUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.DeleteProductUseCase.DeleteProductUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindProductByIdUseCase.FindProductByIdUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindProductsByCategoryUseCase.FindProductsByCategoryUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindProductsUseCase.FindProductsUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.UpdateProductUseCase.UpdateProductUseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SuppressWarnings("rawtypes")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String BASE_URL;
    private final ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private CreateProductUseCase createProductUseCase;

    @MockBean
    private DeleteProductUseCase deleteProductUseCase;

    @MockBean
    private FindProductByIdUseCase findProductByIdUseCase;

    @MockBean
    private FindProductsByCategoryUseCase findProductsByCategoryUseCase;

    @MockBean
    private FindProductsUseCase findProductsUseCase;

    @MockBean
    private UpdateProductUseCase updateProductUseCase;

    @BeforeEach
    public void setUp(){
        this.BASE_URL = String.format("http://localhost:%d", port);
    }

    @Test
    void listTest_Success() {
        Product product1 = new ProductBuilder().build();
        Product product2 = new ProductBuilder().build();

        when(findProductsUseCase.execute()).thenReturn(List.of(product1,product2));

        ResponseEntity<List> resultList  = this.restTemplate.getForEntity(getFullUrl("/product"),
                List.class);

        List<Product> products = new ArrayList<>();
        for(Object object : Objects.requireNonNull(resultList.getBody())){
            products.add(mapper.convertValue(object, Product.class));
        }

        assertEquals(resultList.getStatusCode(), HttpStatus.OK);
        assertEquals(products.size(),2);
        assertTrue(products.contains(product2));
        assertTrue(products.contains(product1));
    }

    @Test
    void listByCategoryTest_Success()  {
        Product product1 = new ProductBuilder().build();
        Product product2 = new ProductBuilder().build();

        when(findProductsByCategoryUseCase.execute(EProductCategory.SNACK)).thenReturn(List.of(product1,product2));

        ResponseEntity<List> resultList  = this.restTemplate.getForEntity(getFullUrl("/product?category=SNACK"),
                List.class);

        List<Product> products = new ArrayList<>();
        for(Object object : Objects.requireNonNull(resultList.getBody())){
            products.add(mapper.convertValue(object, Product.class));
        }

        assertEquals(resultList.getStatusCode(), HttpStatus.OK);
        assertEquals(products.size(),2);
        assertTrue(products.contains(product2));
        assertTrue(products.contains(product1));
    }

    @Test
    void listByCategoryTest_BadRequest()  {
        Product product1 = new ProductBuilder().build();
        Product product2 = new ProductBuilder().build();

        when(findProductsByCategoryUseCase.execute(EProductCategory.SNACK)).thenReturn(List.of(product1,product2));

        ResponseEntity<MessageResponse> resultList  = this.restTemplate.getForEntity(getFullUrl("/product?category=SNAC"),
                MessageResponse.class);

        assertEquals(resultList.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void listCategoryTest_Success() {
        ResponseEntity<List> resultList  = this.restTemplate.getForEntity(getFullUrl("/productCategory"),
                List.class);

        List<EProductCategory> categories = new ArrayList<>();
        for(Object object : Objects.requireNonNull(resultList.getBody())){
            categories.add(mapper.convertValue(object, EProductCategory.class));
        }

        assertEquals(resultList.getStatusCode(), HttpStatus.OK);
        assertEquals(categories.size(),5);
        assertTrue(categories.contains(EProductCategory.SNACK));
        assertTrue(categories.contains(EProductCategory.DESSERT));
        assertTrue(categories.contains(EProductCategory.SIDE_DISH));
        assertTrue(categories.contains(EProductCategory.DRINK));
        assertTrue(categories.contains(EProductCategory.CUSTOM_SNACK));
    }

    @Test
    void getByIdTest_Success() throws ResourceNotFoundException {
        Product product = new ProductBuilder().build();

        when(findProductByIdUseCase.execute(product.getId())).thenReturn(product);

        ResponseEntity<Product> result = this.restTemplate.getForEntity(getFullUrl("/product/" + product.getId()),
                Product.class);

        Product productResult = result.getBody();

        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(product,productResult);
    }

    @Test
    void getByIdTest_NotFound() throws ResourceNotFoundException {
        UUID id = UUID.randomUUID();

        when(findProductByIdUseCase.execute(id)).thenThrow(ResourceNotFoundException.class);

        ResponseEntity<MessageResponse> result = this.restTemplate.getForEntity(getFullUrl("/product/" + id),
                MessageResponse.class);

        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void getByIdTest_BadRequest() {
        String id = "4683433";

        ResponseEntity<MessageResponse> result = this.restTemplate.getForEntity(getFullUrl("/product/" + id),
                MessageResponse.class);

        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void createTest_Success() throws ValidationException {
        Product product = new ProductBuilder().withId(null).build();
        Product createdProduct = new ProductBuilder().build();

        when(createProductUseCase.execute(any())).thenReturn(createdProduct);

        ResponseEntity<Product> result = this.restTemplate.postForEntity(
                getFullUrl("/product"),
                product,
                Product.class
        );

        Product productResult = result.getBody();

        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals(createdProduct, productResult);
    }

    @Test
    void createTest_BadRequest() throws ValidationException {
        Product product = new ProductBuilder().withId(null).build();

        when(createProductUseCase.execute(any())).thenThrow(ValidationException.class);

        ResponseEntity<MessageResponse> result = this.restTemplate.postForEntity(
                getFullUrl("/product"),
                product,
                MessageResponse.class
        );

        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void editTest_Success() throws ValidationException, ResourceNotFoundException, IllegalAccessException {
        Product product = new ProductBuilder().build();

        HttpEntity<Product> entity = new HttpEntity<>(product);

        when(updateProductUseCase.execute(product.getId(), product)).thenReturn(product);

        ResponseEntity<Product> result = this.restTemplate.exchange(
                getFullUrl("/product/" + product.getId()),
                HttpMethod.PATCH,
                entity,
                Product.class
        );

        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void editTest_NotFound() throws ValidationException, ResourceNotFoundException, IllegalAccessException {
        Product product = new ProductBuilder().build();

        HttpEntity<Product> entity = new HttpEntity<>(product);

        when(updateProductUseCase.execute(product.getId(), product)).thenThrow(ResourceNotFoundException.class);

        ResponseEntity<MessageResponse> result = this.restTemplate.exchange(
                getFullUrl("/product/" + product.getId()),
                HttpMethod.PATCH,
                entity,
                MessageResponse.class
        );

        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void editTest_BadRequest() throws ValidationException, ResourceNotFoundException, IllegalAccessException {
        Product product = new ProductBuilder().build();

        HttpEntity<Product> entity = new HttpEntity<>(product);

        when(updateProductUseCase.execute(product.getId(), product)).thenThrow(ValidationException.class);

        ResponseEntity<MessageResponse> result = this.restTemplate.exchange(
                getFullUrl("/product/" + product.getId()),
                HttpMethod.PATCH,
                entity,
                MessageResponse.class
        );

        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void deleteTest_Success() {
        UUID id = UUID.randomUUID();

        ResponseEntity<MessageResponse> result = this.restTemplate.exchange(
                getFullUrl("/product/" + id),
                HttpMethod.DELETE,
                null,
                MessageResponse.class
        );

        MessageResponse response = result.getBody();

        assertTrue(Objects.nonNull(response));
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getMessage(), "Product successfully deleted.");
    }

    @Test
    void deleteTest_MethodNotAllowed() throws ResourceNotFoundException {
        UUID id = UUID.randomUUID();

        doThrow(UsedProductCannotBeDeletedException.class).doNothing().when(deleteProductUseCase).execute(id);

        ResponseEntity<MessageResponse> result = this.restTemplate.exchange(
                getFullUrl("/product/" + id),
                HttpMethod.DELETE,
                null,
                MessageResponse.class
        );

        assertEquals(result.getStatusCode(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Test
    void deleteTest_NotFound() throws ResourceNotFoundException {
        UUID id = UUID.randomUUID();

        doThrow(ResourceNotFoundException.class).doNothing().when(deleteProductUseCase).execute(id);

        ResponseEntity<MessageResponse> result = this.restTemplate.exchange(
                getFullUrl("/product/" + id),
                HttpMethod.DELETE,
                null,
                MessageResponse.class
        );

        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    private String getFullUrl(String endpoint){
        return BASE_URL + endpoint;
    }
}
