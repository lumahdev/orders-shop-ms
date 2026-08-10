package dev.lumah.orders_ms.client.dto;

import java.math.BigDecimal;

public record ProductResponse(
        String id,
        String name,
        BigDecimal price,
        Integer stock,
        Boolean active
) { }