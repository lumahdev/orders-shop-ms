package dev.lumah.orders_ms.model;

import lombok.Data;

@Data
public class OrderItem {

    private String productId;
    private Integer quantity;
}
