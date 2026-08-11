package dev.lumah.orders_ms.service;

import dev.lumah.orders_ms.dto.request.CreatePaymentRequest;
import dev.lumah.orders_ms.dto.response.PaymentResponse;
import dev.lumah.orders_ms.dto.response.PaymentResponse;
import dev.lumah.orders_ms.model.Order;
import dev.lumah.orders_ms.model.Payment;
import dev.lumah.orders_ms.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentResponse createPayment(CreatePaymentRequest dto) {

        Payment payment = new Payment();
        
        payment.setUserId(dto.userId());
        payment.setOrderId(dto.orderId());
        payment.setTotal(dto.total());
        payment.setPaymentStatus(dto.paymentStatus());
        payment.setPaymentType(dto.paymentType());

        Payment savedPayment = paymentRepository.save(payment);

        return PaymentResponse.toDto(savedPayment);
    }

    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(PaymentResponse::toDto)
                .toList();
    }

    public PaymentResponse getPaymentById(String id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Pagamento não encontrado."));
        return PaymentResponse.toDto(payment);
    }
}
