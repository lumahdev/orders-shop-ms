package dev.lumah.orders_ms.dto.response;

import dev.lumah.orders_ms.model.Address;
import dev.lumah.orders_ms.model.OrderStatus;
import dev.lumah.orders_ms.model.Order;

import java.math.BigDecimal;
import java.util.List;

//@Builder
public record OrderResponse(
        String id,
        String userId,
        List<OrderItemResponse> items,
        OrderStatus status,
        BigDecimal total,
        BigDecimal discount,
        String userMail,
        String userName,
        String userPhone,
        Address userAddress) {

    public static OrderResponse toDto(Order order) {

        List<OrderItemResponse> items = order.getItems()
                .stream()
                .map(OrderItemResponse::toDto)
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                items,
                order.getStatus(),
                order.getTotal(),
                order.getDiscount(),
                order.getUserMail(),
                order.getUserName(),
                order.getUserPhone(),
                order.getUserAddress()
        );
    }
}
