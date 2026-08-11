package dev.lumah.orders_ms.dto.request;

import dev.lumah.orders_ms.model.PaymentStatus;
import dev.lumah.orders_ms.model.PaymentType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreatePaymentRequest(
        @NotNull
        String orderId,

        @NotNull
        String userId,

        @NotNull
        BigDecimal total,

        @NotNull
        PaymentType paymentType,

        @NotNull
        PaymentStatus paymentStatus

) { }
