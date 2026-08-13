package dev.lumah.orders_ms.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> body = Map.of(
            "error", ex.getClass().getSimpleName(),
            "message", ex.getMessage(),
            "timestamp", LocalDateTime.now()
        );
        return ResponseEntity.internalServerError().body(body);
    }
}
