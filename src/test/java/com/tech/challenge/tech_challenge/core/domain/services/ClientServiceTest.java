package com.tech.challenge.tech_challenge.core.domain.services;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.ClientRepository;
import com.tech.challenge.tech_challenge.core.application.exceptions.ResourceNotFoundException;
import com.tech.challenge.tech_challenge.core.application.exceptions.ValidationException;
import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    @Test
    public void listTest(){
        Client client1 = new Client();
        Client client2 = new Client();

        client1.setId(UUID.randomUUID());
        client2.setId(UUID.randomUUID());

        when(clientRepository.findAll()).thenReturn(List.of(client1,client2));

        List<Client> clients = clientService.list();

        assertEquals(clients.size(), 2);
        assertTrue(clients.contains(client1));
        assertTrue(clients.contains(client2));
    }

    @Test
    public void getByIdTest_Success() throws Exception {
        Client client = new Client();

        client.setId(UUID.randomUUID());

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        Client clientResult = clientService.getById(client.getId());

        assertEquals(client, clientResult);
    }

    @Test
    public void getByIdTest_Exception() throws Exception {
        UUID id = UUID.randomUUID();

        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()->{
            clientService.getById(id);
        });
    }

    @Test
    public void getByCpfTest_Success() throws Exception {
        String cpf = "123456";
        Client client = new Client();

        client.setId(UUID.randomUUID());
        client.setCpf(cpf);

        when(clientRepository.findByCpf(cpf)).thenReturn(Optional.of(client));

        Client clientResult = clientService.getByCpf(cpf);

        assertEquals(client, clientResult);
        assertEquals(client.getCpf(),cpf);
    }

    @Test
    public void getByCpfTest_Exception() throws Exception {
        String cpf = "123456";

        when(clientRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()->{
            clientService.getByCpf(cpf);
        });
    }

    @Test
    public void createTest_Success() throws Exception {
        Client client = mock(Client.class);

        when(clientRepository.save(client)).thenReturn(client);

        Client clientResult = clientService.create(client);

        assertEquals(client, clientResult);
    }

    @Test
    public void createTest_Exception() throws Exception {
        Client client = mock(Client.class);

        when(clientRepository.save(client)).thenReturn(client);
        doThrow(ValidationException.class).doNothing().when(client).validate();

        assertThrows(ValidationException.class, ()->{
            clientService.create(client);
        });
    }
}
