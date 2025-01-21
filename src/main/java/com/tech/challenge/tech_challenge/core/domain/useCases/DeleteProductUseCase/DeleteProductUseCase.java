package com.tech.challenge.tech_challenge.core.domain.useCases.DeleteProductUseCase;

import com.tech.challenge.tech_challenge.core.application.exceptions.UsedProductCannotBeDeletedException;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;
import com.tech.challenge.tech_challenge.core.domain.repositories.IProductRepository;
import com.tech.challenge.tech_challenge.core.domain.useCases.FindProductByIdUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteProductUseCase implements IDeleteProductUseCase {
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private FindProductByIdUseCase findProductByIdUseCase;

    public void execute(UUID id) {
        Product product = findProductByIdUseCase.execute(id);

        try {
            productRepository.delete(product);
        }catch (DataIntegrityViolationException ex){
            throw new UsedProductCannotBeDeletedException(id.toString(), ex);
        }
    }
}
