package dev.lumah.orders_ms.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateUserRequest(

        @NotNull(message = "O nome do usuário é obrigatório.")
        @NotBlank(message = "Não pode ser vazio.")
        @Size(min = 2, max = 50, message = "O nome do usuário deve conter entre 2 e 50 caracteres.")
        String name,

        @NotNull(message = "E-mail do usuário é obrigatório.")
        @Email(message = "E-mail do usuário deve ser válido.")
        @NotBlank(message = "Não pode ser vazio.")
        @Size(max = 80, message = "E-mail do usuário deve conter no máximo 80 caracteres.")
        String email,

        @NotNull(message = "A senha do usuário é obrigatória.")
        @NotBlank(message = "Não pode ser vazio.")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,20}$",
                message = "A senha deve conter entre 8 e 20 caracteres, pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial."
        )
        String password,

        @NotNull(message = "Telefone do usuário é obrigatório.")
        @NotBlank(message = "Não pode ser vazio.")
        @Pattern(
                regexp = "^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$",
                message = "Telefone inválido."
        )
        String phone,

        @Valid
        @NotNull
        CreateAddressRequest address,

        @PastOrPresent
        LocalDate creationDate,

        Boolean active
) { }
