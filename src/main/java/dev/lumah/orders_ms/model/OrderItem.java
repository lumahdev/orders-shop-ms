package dev.lumah.orders_ms.model;

import lombok.*;

import java.math.BigDecimal;

@Data
public class OrderItem {

    private String productId;
    private Integer quantity;
    private String name;
    private BigDecimal price;
    private BigDecimal discount;
}
