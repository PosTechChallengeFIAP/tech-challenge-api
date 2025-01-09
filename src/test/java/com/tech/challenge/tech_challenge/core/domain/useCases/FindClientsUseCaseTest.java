package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.ClientRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FindClientsUseCaseTest {

    @Autowired
    private FindClientsUseCase findClientsUseCase;

    @MockBean
    private ClientRepository clientRepository;

    @Test
    public void listTest(){
        Client client1 = new Client();
        Client client2 = new Client();

        client1.setId(UUID.randomUUID());
        client2.setId(UUID.randomUUID());

        when(clientRepository.findAll()).thenReturn(List.of(client1,client2));

        List<Client> clients = findClientsUseCase.execute();

        assertEquals(clients.size(), 2);
        assertTrue(clients.contains(client1));
        assertTrue(clients.contains(client2));
    }
}
