package dev.lumah.orders_ms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CantSendException extends RuntimeException {
	public CantSendException() {
		super("Não é possível enviar este pedido.");
	}

	public CantSendException(String message) {
		super(message);
	}
}
