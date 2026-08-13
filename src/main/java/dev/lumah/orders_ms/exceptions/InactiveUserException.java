package dev.lumah.orders_ms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InactiveUserException extends RuntimeException {
	public InactiveUserException() {
		super("Usuário inativo.");
	}

	public InactiveUserException(String message) {
		super(message);
	}
}
