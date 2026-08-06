package dev.lumah.orders_ms.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record CreateOrderRequest(
        @NotNull(message = "Id do usuário é obrigatório")
        String userId,

        @NotEmpty(message = "O pedido deve conter pelo menos um item.")
        @Valid
        List<OrderItemRequest> items
) {}
