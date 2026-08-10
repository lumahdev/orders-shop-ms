package dev.lumah.orders_ms.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RemoteServiceException.class)
    public ResponseEntity<String> handleRemoteServiceException(
            RemoteServiceException exception
    ) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(exception.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(
            BusinessException exception
    ) {
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }
}
