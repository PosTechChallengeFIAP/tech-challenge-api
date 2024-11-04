package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.challenge.tech_challenge.core.domain.entities.Queue;
import com.tech.challenge.tech_challenge.core.domain.services.QueueService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Queue", description = "queue endpoints")
public class QueueController {
    
    @Autowired
    private QueueService queueService;

    @GetMapping("/queue")
    @Operation(summary = "Finds all not finished queue orders", description = "This endpoint is used to find all queue not finished",
            tags = {"Queue"},
            responses ={
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            }
    )
    public List<Queue> findAllPendingQueue() {
        return this.queueService.findNotFinishedItems();
    }
}
