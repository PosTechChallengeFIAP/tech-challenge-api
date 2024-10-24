package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.challenge.tech_challenge.core.domain.entities.Queue;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Integer> {
}
