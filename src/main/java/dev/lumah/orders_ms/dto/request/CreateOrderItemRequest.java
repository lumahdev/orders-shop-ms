package dev.lumah.orders_ms.dto.request;

import dev.lumah.orders_ms.model.OrderItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateOrderItemRequest(

        @NotBlank(message = "Id do produto é obrigatório.")
        String productId,

        @NotNull(message = "A quantidade é obrigatória.")
        @Min(value = 1, message = "A quantidade deve ser maior que zero.")
        Integer quantity
) {

    public OrderItem toEntity() {
        return new OrderItem(productId, quantity);
    }
}
