package dev.lumah.orders_ms.dto;

import dev.lumah.orders_ms.model.Product;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        String id,
        String name,
        String description,
        BigDecimal price,
        BigDecimal discount,
        Integer stock,
        Boolean active) {

    public static ProductResponse toDto(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getDiscount(), product.getStock(), product.getActive());
    }
}
