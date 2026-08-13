package dev.lumah.orders_ms.service;

import dev.lumah.orders_ms.dto.request.CreatePaymentRequest;
import dev.lumah.orders_ms.dto.response.OrderResponse;
import dev.lumah.orders_ms.dto.response.PaymentResponse;
import dev.lumah.orders_ms.exceptions.*;
import dev.lumah.orders_ms.model.*;
import dev.lumah.orders_ms.repository.PaymentRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private UserService userService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Value("${rabbitmq.notification.exchange}")
    private String NOTIFICATION_EXCHANGE;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private Payment findPayment(String id) {
        return paymentRepository.findById(id).orElseThrow(PaymentNotFoundException::new);
    }

    public PaymentResponse createPayment(CreatePaymentRequest dto) {

        User user = userService.findUser(dto.userId());

        if (!Boolean.TRUE.equals(user.getActive())) {
            throw new InactiveUserException();
        }

        Order order = orderService.findOrder(dto.orderId());

        if(order.getStatus() != OrderStatus.PAYMENT_PENDING){
            throw new CantPayException();
        }

        if(!Objects.equals(dto.total(), order.getTotal())) {
            throw new InsufficientPaymentException();
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

        OrderResponse orderDto = OrderResponse.toDto(order);

        switch(payment.getPaymentStatus()) {
            case PaymentStatus.APPROVED -> rabbitTemplate.convertAndSend(NOTIFICATION_EXCHANGE, "email.order.paid",orderDto);
            case PaymentStatus.REFUSED -> rabbitTemplate.convertAndSend(NOTIFICATION_EXCHANGE, "email.order.payment-denied", orderDto);
            default -> throw new InvalidPaymentException();
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
