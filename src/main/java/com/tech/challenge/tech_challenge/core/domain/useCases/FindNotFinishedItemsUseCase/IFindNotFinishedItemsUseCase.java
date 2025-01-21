package com.tech.challenge.tech_challenge.core.domain.useCases.FindNotFinishedItemsUseCase;

import java.util.List;

import com.tech.challenge.tech_challenge.core.domain.entities.Queue;

public interface IFindNotFinishedItemsUseCase {
    List<Queue> execute();
}
