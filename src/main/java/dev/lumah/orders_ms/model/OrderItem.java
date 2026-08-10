package dev.lumah.orders_ms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderItem {

    private String productId;
    private Integer quantity;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal discount;

    public OrderItem(String id, Integer quantity) {
        this.productId = id;
        this.quantity = quantity;
    }
}
