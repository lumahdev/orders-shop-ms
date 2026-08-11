package dev.lumah.orders_ms.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderItem {

    private String productId;
    private Integer quantity;
    private String name;
    private BigDecimal price;
    private BigDecimal discount;

    public OrderItem(String productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
