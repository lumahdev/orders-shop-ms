package dev.lumah.orders_ms.client.dto;

public record AddressResponse(
        String cep,
        String street,
        String number,
        String additional,
        String neighborhood,
        String state
) {
}
