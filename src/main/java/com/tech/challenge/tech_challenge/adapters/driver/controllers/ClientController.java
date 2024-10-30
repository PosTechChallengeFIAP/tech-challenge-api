package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import com.tech.challenge.tech_challenge.core.domain.services.ClientService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@Tag(name = "Client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/client")
    public List<Client> all(@RequestParam(required = false) String cpf) throws Exception {
        if(StringUtils.isEmpty(cpf))
            return clientService.list();
        else
            return List.of(clientService.getByCpf(cpf));
    }

    @GetMapping("/client/{id}")
    public Client one(@PathVariable UUID id) throws Exception {
        return clientService.getById(id);
    }

    @PostMapping("/client")
    public Client newClient(@RequestBody Client client){
        return clientService.create(client);
    }
}

