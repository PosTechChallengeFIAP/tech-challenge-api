package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import com.tech.challenge.tech_challenge.core.domain.repositories.IProductRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CreateProductUseCaseTest {
    @MockBean
    private IProductRepository productRepository;

    @Autowired
    private CreateProductUseCase createProductUseCase;

    @Test
    public void createTest_Success() throws Exception {
        Product product = mock(Product.class);

        when(productRepository.save(product)).thenReturn(product);

        Product productResult = createProductUseCase.execute(product);

        assertEquals(product, productResult);
    }

    @Test
    public void createTest_Exception() throws Exception {
        Product product = mock(Product.class);

        when(productRepository.save(product)).thenReturn(product);
        doThrow(ValidationException.class).doNothing().when(product).validate();

        assertThrows(ValidationException.class, ()->{
            createProductUseCase.execute(product);
        });
    }
}
