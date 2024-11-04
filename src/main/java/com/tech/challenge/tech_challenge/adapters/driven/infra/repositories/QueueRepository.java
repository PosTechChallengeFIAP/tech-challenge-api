package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tech.challenge.tech_challenge.core.domain.entities.Queue;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Integer> {
    @Query(value = "SELECT * FROM queue WHERE status <> 5", nativeQuery = true)
    List<Queue> findAllNotFinishedItems();
}
