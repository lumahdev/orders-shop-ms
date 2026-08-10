package dev.lumah.orders_ms.client.dto;

import java.time.LocalDate;

public record UserResponse(
        String id,
        String name,
        String email,
        String password,
        String phone,
        AddressResponse address,
        LocalDate creationDate,
        Boolean active
) { }
