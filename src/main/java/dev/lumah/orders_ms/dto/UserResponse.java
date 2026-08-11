package dev.lumah.orders_ms.dto;

import dev.lumah.orders_ms.model.Address;
import dev.lumah.orders_ms.model.User;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserResponse(
        String id,
        String name,
        String email,
        String password,
        String phone,
        Address address,
        LocalDate creationDate,
        Boolean active
) {
    public static UserResponse toDto(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getPhone(), user.getAddress(), user.getCreationDate(), user.getActive());
    }
}
