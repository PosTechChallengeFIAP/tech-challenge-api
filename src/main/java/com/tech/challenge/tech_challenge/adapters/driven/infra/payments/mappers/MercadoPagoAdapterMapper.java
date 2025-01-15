package com.tech.challenge.tech_challenge.adapters.driven.infra.payments.mappers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mercadopago.client.preference.PreferenceItemRequest;
import com.tech.challenge.tech_challenge.core.application.dtos.GeneratePaymentLinkRequestDTO;

public class MercadoPagoAdapterMapper {
    public static List<PreferenceItemRequest> toPreferenceItemRequest(List<GeneratePaymentLinkRequestDTO> itemsToTransform) {
        List<PreferenceItemRequest> items = new ArrayList<>();

        itemsToTransform.forEach((itemToTransform) -> {
            PreferenceItemRequest itemToAdd = PreferenceItemRequest.builder()
                .id(itemToTransform.getItemId())
                .title(itemToTransform.getDescription())
                .quantity(itemToTransform.getQuantity())
                .unitPrice(new BigDecimal(itemToTransform.getAmount()))
                .build();
            items.add(itemToAdd);
        });

        return items;
    }
}
