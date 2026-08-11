package dev.lumah.orders_ms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateAddressRequest(

        @NotNull(message = "O CEP é obrigatório.")
        @NotBlank(message = "O CEP é obrigatório.")
        @Pattern(
                regexp = "^\\d{5}-?\\d{3}$",
                message = "CEP inválido."
        )
        String cep,

        @NotNull
        @NotBlank(message = "A rua é obrigatória.")
        @Size(min = 5, max = 50)
        String street,

        @NotNull
        @Size(max = 50)
        @NotBlank(message = "O número é obrigatório.")
        String number,

        @Size(max = 50)
        String additional,

        @NotNull
        @Size(max = 50)
        @NotBlank(message = "O bairro é obrigatório.")
        String neighborhood,

        @Size(max = 50)
        @NotBlank(message = "O estado é obrigatório.")
        String state
) { }
