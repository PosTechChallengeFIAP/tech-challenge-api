package com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.jpa;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.orm.QueueEntity;

public interface QueueRepositoryJPA extends JpaRepository<QueueEntity, Integer> {
    @Query(value = "SELECT * FROM queue WHERE status IN (0,1,2) ORDER BY CASE WHEN status = 2 THEN 1 WHEN status = 1 THEN 2 ELSE 3 END, created_at ASC;", nativeQuery = true)
    List<QueueEntity> findAllNotFinishedItems();
}
