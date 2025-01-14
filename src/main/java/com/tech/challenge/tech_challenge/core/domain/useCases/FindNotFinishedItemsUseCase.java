package com.tech.challenge.tech_challenge.core.domain.useCases;

import com.tech.challenge.tech_challenge.adapters.driven.infra.repositories.QueueRepository;
import com.tech.challenge.tech_challenge.core.domain.entities.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindNotFinishedItemsUseCase {

    @Autowired
    private QueueRepository queueRepository;

    public List<Queue> execute() {
        return this.queueRepository.findAllNotFinishedItems();
    }

}
