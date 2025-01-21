package com.tech.challenge.tech_challenge.core.domain.useCases.AddPaymentToOrderUseCase.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.tech.challenge.tech_challenge.core.application.dtos.GeneratePaymentLinkRequestDTO;
import com.tech.challenge.tech_challenge.core.domain.entities.Order;
import com.tech.challenge.tech_challenge.core.domain.entities.OrderItem;
import com.tech.challenge.tech_challenge.core.domain.entities.Product;

public class AddPaymentToOrderMapper {
    public static  List<GeneratePaymentLinkRequestDTO> orderToPaymentLinkRequest(Order order) {
        List<GeneratePaymentLinkRequestDTO> list = new ArrayList<>();
        
        Set<OrderItem> items = order.getOrderItems();

        items.forEach(item -> {
            Product product = item.getProduct();
            String productId = product.getId().toString();
            double productPrice = product.getPrice();
            String productName = product.getName();
            int quantity = item.getQuantity();

            GeneratePaymentLinkRequestDTO requestItem = new GeneratePaymentLinkRequestDTO(
                productId,
                productPrice,
                productName,
                quantity
            );

            list.add(requestItem);
        });

        return list;
    }
}
