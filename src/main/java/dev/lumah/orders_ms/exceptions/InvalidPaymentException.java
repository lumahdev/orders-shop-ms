package dev.lumah.orders_ms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPaymentException extends RuntimeException {
	public InvalidPaymentException() {
		super("Total pago insuficiente.");
	}

	public InvalidPaymentException(String message) {
		super(message);
	}
}
