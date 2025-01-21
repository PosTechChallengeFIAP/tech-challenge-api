package com.tech.challenge.tech_challenge.adapters.driver.controllers;

import java.util.List;

import com.tech.challenge.tech_challenge.core.domain.useCases.FindNotFinishedItemsUseCase.IFindNotFinishedItemsUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.UpdateItemQueueUseCase.IUpdateItemQueueUseCase;
import com.tech.challenge.tech_challenge.core.domain.useCases.UpdateItemQueueUseCase.DTOs.UpdateItemQueueUseCaseRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RestController;

import com.tech.challenge.tech_challenge.core.domain.entities.Queue;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Queue", description = "queue endpoints")
public class QueueController {

    @Autowired
    private IFindNotFinishedItemsUseCase findNotFinishedItemsUseCase;

    @Autowired
    private IUpdateItemQueueUseCase updateItemQueueUseCase;

    @GetMapping("/queue")
    @Operation(summary = "Finds all not finished queue orders", description = "This endpoint is used to find all queue not finished", tags = {
            "Queue" }, responses = {
                    @ApiResponse(description = "Success", responseCode = "200"),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public List<Queue> findAllPendingQueue() {
        return this.findNotFinishedItemsUseCase.execute();
    }

    @SuppressWarnings("rawtypes")
    @PatchMapping("/queue/{queueItemId}")
    @Operation(summary = "Finds all not finished queue orders", description = "This endpoint is used to update an item queue", tags = {
            "Queue" }, responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(schema = @Schema(implementation = Queue.class))
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized Access", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity updateItemQueue(@PathVariable Integer queueItemId, @RequestBody Queue queue) {
        try {
            UpdateItemQueueUseCaseRequest request = new UpdateItemQueueUseCaseRequest(
                    queueItemId,
                    queue.getStatus());
            Queue useCaseResponse = updateItemQueueUseCase.execute(request);
            return ResponseEntity.status(HttpStatus.OK).body(useCaseResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}
