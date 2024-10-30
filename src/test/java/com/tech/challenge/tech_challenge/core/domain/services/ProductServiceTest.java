package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.ProductRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.UsedProductCannotBeDeletedException;
import com.tech.challenge.tech_challenge.core.domain.entities.EProductCategory;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import com.tech.challenge.tech_challenge.core.domain.services.generic.Patcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private Patcher<Product> productPatcher;

    @Test
    public void listTest(){
        Product product1 = new Product();
        Product product2 = new Product();

        product1.setId(UUID.randomUUID());
        product2.setId(UUID.randomUUID());

        when(productRepository.findAll()).thenReturn(List.of(product1,product2));

        List<Product> products = productService.list();

        assertEquals(products.size(), 2);
        assertTrue(products.contains(product1));
        assertTrue(products.contains(product2));
    }

    @Test
    public void listByCategoryTest(){
        Product product1 = new Product();

        product1.setId(UUID.randomUUID());
        product1.setCategory(EProductCategory.SIDE_DISH);

        when(productRepository.findProductByCategory(EProductCategory.SIDE_DISH.ordinal())).thenReturn(List.of(product1));

        List<Product> products = productService.listByCategory(EProductCategory.SIDE_DISH);

        assertEquals(products.size(), 1);
        assertTrue(products.contains(product1));
        assertEquals(products.get(0).getCategory(), EProductCategory.SIDE_DISH);
    }

    @Test
    public void getByIdTest_Success() throws Exception {
        Product product = new Product();

        product.setId(UUID.randomUUID());

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Product productResult = productService.getById(product.getId());

        assertEquals(product, productResult);
    }

    @Test
    public void getByIdTest_Exception() throws Exception {
        UUID id = UUID.randomUUID();

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        Exception ex = assertThrows(Exception.class, ()->{
            productService.getById(id);
        });

        assertEquals(ex.getMessage(), "Unable to Find Product");
    }

    @Test
    public void createTest_Success() throws Exception {
        Product product = mock(Product.class);

        when(productRepository.save(product)).thenReturn(product);
        when(product.validate()).thenReturn(null);

        Product productResult = productService.create(product);

        assertEquals(product, productResult);
    }

    @Test
    public void createTest_Exception() throws Exception {
        Product product = mock(Product.class);
        Error error = mock(Error.class);

        when(productRepository.save(product)).thenReturn(product);
        when(product.validate()).thenReturn(error);

        Error errorResult = assertThrows(Error.class, ()->{
            productService.create(product);
        });

        assertEquals(error, errorResult);
    }

    @Test
    public void deleteTest() throws Exception {
        Product product = mock(Product.class);
        Error error = mock(Error.class);

        doThrow(DataIntegrityViolationException.class).when(productRepository).delete(product);
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(product.validate()).thenReturn(error);

        UsedProductCannotBeDeletedException ex = assertThrows(UsedProductCannotBeDeletedException.class, ()->{
            productService.delete(UUID.randomUUID());
        });
    }

    @Test
    public void updateTest() throws Exception {
        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);

        when(productRepository.findById(any())).thenReturn(Optional.of(product1));
        when(productPatcher.execute(product1, product2)).thenReturn(product1);
        when(productRepository.save(product1)).thenReturn(product1);

        Product productResult = productService.update(UUID.randomUUID(), product2);

        assertEquals(product1, productResult);
    }
}
