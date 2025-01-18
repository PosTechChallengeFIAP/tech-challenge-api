package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.application.util.CPFValidator;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.repositories.IClientRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FindClientByCpfUseCaseTest {
    @Autowired
    private FindClientByCpfUseCase findClientByCpfUseCase;

    @MockBean
    private IClientRepository clientRepository;
    @Test
    public void getByCpfTest_Success() throws ValidationException, ResourceNotFoundException {
        String cpf = "71595107045";
        String formattedCpf = CPFValidator.formatCPF(cpf);
        Client client = new Client();

        client.setId(UUID.randomUUID());
        client.setCpf(cpf);

        when(clientRepository.findByCpf(formattedCpf)).thenReturn(Optional.of(client));

        Client clientResult = findClientByCpfUseCase.execute(cpf);

        assertEquals(client, clientResult);
        assertEquals(client.getCpf(),cpf);
    }

    @Test
    public void getByCpfTest_NotFound() throws ValidationException {
        String cpf = "71595107045";
        String formattedCpf = CPFValidator.formatCPF(cpf);

        when(clientRepository.findByCpf(formattedCpf)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()->{
            findClientByCpfUseCase.execute(cpf);
        });
    }

    @Test
    public void getByCpfTest_Invalid() throws ValidationException {
        String cpf = "8787678";

        assertThrows(ValidationException.class, ()->{
            findClientByCpfUseCase.execute(cpf);
        });
    }
}
