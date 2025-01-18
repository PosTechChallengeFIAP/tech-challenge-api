package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepositoryJPA extends JpaRepository<ClientEntity, UUID> {
    @Query("SELECT c FROM Client c WHERE c.cpf =:cpf")
    Optional<ClientEntity>  findByCpf ( @Param("cpf") String cpf);
}
