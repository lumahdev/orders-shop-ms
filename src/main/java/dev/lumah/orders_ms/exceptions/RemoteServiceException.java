package dev.lumah.orders_ms.exceptions;

import org.springframework.http.HttpStatusCode;

public class RemoteServiceException extends RuntimeException {

    private final HttpStatusCode status;

    public RemoteServiceException(
            HttpStatusCode status,
            String message
    ) {
        super(message);
        this.status = status;
    }

    public HttpStatusCode getStatus() {
        return status;
    }
}
