package dev.lumah.orders_ms.exceptions;

public class CantPayException extends RuntimeException {
	public CantPayException() {
		super("Não é possível pagar este pedido.");
	}

	public CantPayException(String message) {
		super(message);
	}
}
