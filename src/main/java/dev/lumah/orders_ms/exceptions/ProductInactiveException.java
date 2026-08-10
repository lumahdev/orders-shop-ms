package dev.lumah.orders_ms.exceptions;

public class ProductInactiveException extends RuntimeException {

    public ProductInactiveException(String message) {
        super(message);
    }
}
