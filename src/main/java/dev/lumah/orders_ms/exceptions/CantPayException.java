package dev.lumah.orders_ms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CantPayException extends RuntimeException {
	public CantPayException() {
		super("Não é possível pagar este pedido.");
	}

	public CantPayException(String message) {
		super(message);
	}
}
