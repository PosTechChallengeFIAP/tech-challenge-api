package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories;

import com.tech.challenge.tech_challenge.core.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    @Query("SELECT c FROM Client c WHERE c.cpf =:cpf")
    Optional<Client>  FindByCpf ( @Param("cpf") String cpf);
}
