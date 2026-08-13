package dev.lumah.orders_ms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CantArriveException extends RuntimeException {
	public CantArriveException() {
		super("Não é possível confirmar que este pedido chegou.");
	}

	public CantArriveException(String message) {
		super(message);
	}
}
