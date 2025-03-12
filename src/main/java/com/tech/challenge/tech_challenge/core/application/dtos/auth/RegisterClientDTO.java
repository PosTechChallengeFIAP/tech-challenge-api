package com.tech.challenge.tech_challenge.core.application.dtos.auth;

import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterClientDTO {
    private String name;
    private String cpf;
    private String email;
    private String password;

    public Client getClient(){
        Client client = new Client();

        client.setName(this.name);
        client.setCpf(this.cpf);
        client.setEmail(this.email);

        return client;
    }
}
