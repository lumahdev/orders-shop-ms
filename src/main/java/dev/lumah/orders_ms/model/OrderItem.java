package dev.lumah.orders_ms.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderItem {

    private String productId;
    private Integer quantity;

    public OrderItem(String id, Integer quantity) {
        this.productId = id;
        this.quantity = quantity;
    }
}
