package dev.lumah.orders_ms.dto;

import dev.lumah.orders_ms.model.OrderItem;
import dev.lumah.orders_ms.model.Status;
import dev.lumah.orders_ms.model.Order;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderResponse(
        String id,
        String userId,
        List<OrderItem> items,
        Status status,
        BigDecimal total) {

    public static OrderResponse toDto(Order order) {
        return new OrderResponse(order.getId(), order.getUserId(), order.getItems(), order.getStatus(), order.getTotal());
    }
}
