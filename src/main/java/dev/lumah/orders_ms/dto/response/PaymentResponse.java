package dev.lumah.orders_ms.dto.response;

import dev.lumah.orders_ms.model.Payment;
import dev.lumah.orders_ms.model.PaymentStatus;
import dev.lumah.orders_ms.model.PaymentType;

import java.math.BigDecimal;

public record PaymentResponse(
        String id,
        String orderId,
        String userId,
        BigDecimal total,
        PaymentType paymentType,
        PaymentStatus paymentStatus
) {
    public static PaymentResponse toDto(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getUserId(),
                payment.getTotal(),
                payment.getPaymentType(),
                payment.getPaymentStatus()
        );
    }
}
