package dev.lumah.orders_ms.dto.response;

import dev.lumah.orders_ms.model.OrderItem;

import java.math.BigDecimal;

public record OrderItemResponse(
        String productId,
        Integer quantity,
        String name,
        BigDecimal price,
        BigDecimal discount
) {

    public static OrderItemResponse toDto(OrderItem item) {
        return new OrderItemResponse(
                item.getProductId(),
                item.getQuantity(),
                item.getName(),
                item.getPrice(),
                item.getDiscount()
        );
    }
}
