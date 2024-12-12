package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tech.challenge.tech_challenge.core.domain.entities.Queue;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Integer> {
    @Query(value = "SELECT * FROM queue WHERE status IN (0,1,2) ORDER BY CASE WHEN status = 2 THEN 1 WHEN status = 1 THEN 2 ELSE 3 END, created_at ASC;", nativeQuery = true)
    List<Queue> findAllNotFinishedItems();
}
