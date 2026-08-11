package dev.lumah.orders_ms.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotNull(message = "O produto deve conter um nome.")
        @NotBlank(message = "O produto deve conter um nome válido.")
        @Size(min = 3, max = 100, message = "O nome do produto deve conter entre 3 a 100 caracteres.")
        String name,

        @NotNull(message = "O produto deve conter uma descrição.")
        @NotBlank(message = "O produto deve conter uma descrição válida.")
        @Size(min = 50, max = 300, message = "A descrição do produto deve conter entre 50 a 100 caracteres.")
        String description,

        @NotNull(message = "O produto deve conter um preço.")
        @DecimalMin(value = "1.00", message = "O preço do produto deve ser pelo menos 1.00.")
        BigDecimal price,

        @DecimalMin(value = "0.00",  message = "O desconto do produto deve ser um valor positivo.")
        @DecimalMax(value = "100.00", message = "O desconto do produto deve ser no máximo 100.")
        BigDecimal discount,

        @NotNull(message = "O produto deve conter um estoque.")
        @Min(value = 1, message = "O estoque do produto deve ser um valor positivo.")
        Integer stock,

        Boolean active
) {}
