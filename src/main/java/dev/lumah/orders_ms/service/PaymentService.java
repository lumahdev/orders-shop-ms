package dev.lumah.orders_ms.service;

import dev.lumah.orders_ms.dto.request.CreatePaymentRequest;
import dev.lumah.orders_ms.dto.response.PaymentResponse;
import dev.lumah.orders_ms.model.*;
import dev.lumah.orders_ms.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentResponse createPayment(CreatePaymentRequest dto) {

        User user = userService.findUser(dto.userId());

        userService.validateUser(user);

        Order order = orderService.findOrder(dto.orderId());

        orderService.validateOrder(order);

        Payment payment = new Payment();
        
        payment.setUserId(user.getId());
        payment.setOrderId(order.getId());
        payment.setTotal(dto.total());
        payment.setPaymentStatus(dto.paymentStatus());
        payment.setPaymentType(dto.paymentType());

        Payment savedPayment = paymentRepository.save(payment);

        if (payment.getPaymentStatus().equals(PaymentStatus.APPROVED)) {
            orderService.changeOrderStatus(order, OrderStatus.PROCESSING);
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
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Pagamento não encontrado."));
        return PaymentResponse.toDto(payment);
    }
}
