package dev.lumah.orders_ms.dto.response;

import dev.lumah.orders_ms.model.Address;

public record AddressResponse(
        String cep,
        String street,
        String number,
        String additional,
        String neighborhood,
        String state
) {
    public static AddressResponse toDto(Address address) {
        return new AddressResponse(
                address.getCep(),
                address.getStreet(),
                address.getNumber(),
                address.getAdditional(),
                address.getNeighborhood(),
                address.getState()
        );
    }
}
