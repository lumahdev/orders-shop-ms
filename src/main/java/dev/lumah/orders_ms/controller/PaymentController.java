package dev.lumah.orders_ms.controller;

import dev.lumah.orders_ms.dto.request.CreatePaymentRequest;
import dev.lumah.orders_ms.dto.response.PaymentResponse;
import dev.lumah.orders_ms.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse createPayment(@RequestBody @Valid CreatePaymentRequest dto) {
        return paymentService.createPayment(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentResponse> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentResponse getPaymentById(@PathVariable String id) {
        return paymentService.getPaymentById(id);
    }

}
