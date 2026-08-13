package dev.lumah.orders_ms.service;

import dev.lumah.orders_ms.dto.request.CreatePaymentRequest;
import dev.lumah.orders_ms.dto.response.PaymentResponse;
import dev.lumah.orders_ms.exceptions.CantPayException;
import dev.lumah.orders_ms.exceptions.InvalidPaymentException;
import dev.lumah.orders_ms.exceptions.PaymentNotFoundException;
import dev.lumah.orders_ms.model.*;
import dev.lumah.orders_ms.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PaymentService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;
//    os services acima podiam ser substituidos por um microsserviço

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ValidationService validationService;

    private Payment findPayment(String id) {
        return paymentRepository.findById(id).orElseThrow(PaymentNotFoundException::new);
    }

    public PaymentResponse createPayment(CreatePaymentRequest dto) {

        User user = validationService.validateUser(dto.userId());

        Order order = validationService.validateOrder(dto.orderId());

        if(!Objects.equals(dto.total(), order.getTotal())) {
            throw new InvalidPaymentException();
        }

        Payment payment = new Payment();
        
        payment.setUserId(user.getId());
        payment.setOrderId(order.getId());
        payment.setTotal(dto.total());
        payment.setPaymentStatus(dto.paymentStatus());
        payment.setPaymentType(dto.paymentType());

        Payment savedPayment = paymentRepository.save(payment);

        if (payment.getPaymentStatus().equals(PaymentStatus.APPROVED)) {
            orderService.changeOrderStatus(order.getId(), OrderStatus.PROCESSING);
            for (OrderItem item : order.getItems()) {
                productService.deduceStock(item.getProductId(), item.getQuantity());
            }
        }

        return PaymentResponse.toDto(savedPayment);
    }

    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(PaymentResponse::toDto)
                .toList();
    }

    public PaymentResponse getPaymentById(String id) {
        return PaymentResponse.toDto(findPayment(id));
    }
}
