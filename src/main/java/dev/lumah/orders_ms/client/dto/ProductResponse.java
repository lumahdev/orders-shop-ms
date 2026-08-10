package dev.lumah.orders_ms.client.dto;

import java.math.BigDecimal;

public record ProductResponse(
        String id,
        String name,
        String description,
        BigDecimal price,
        BigDecimal discount,
        Integer stock,
        Boolean active
) { }