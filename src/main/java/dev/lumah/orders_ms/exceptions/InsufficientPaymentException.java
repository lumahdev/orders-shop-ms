package dev.lumah.orders_ms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientPaymentException extends RuntimeException {
	public InsufficientPaymentException() {
		super("Total pago insuficiente.");
	}

	public InsufficientPaymentException(String message) {
		super(message);
	}
}
