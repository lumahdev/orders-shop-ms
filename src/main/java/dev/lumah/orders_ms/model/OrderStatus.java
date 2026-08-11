package dev.lumah.orders_ms.model;

public enum OrderStatus {
    PAYMENT_PENDING,
    PROCESSING,
    SENT,
    ARRIVED,
    REFUND_ASKED,
    REFUNDED,
    FINISHED,
    CANCELED
}
