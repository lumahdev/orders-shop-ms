package dev.lumah.orders_ms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InactiveProductException extends RuntimeException {
	public InactiveProductException() {
		super("Produto inativo.");
	}

	public InactiveProductException(String message) {
		super(message);
	}
}
