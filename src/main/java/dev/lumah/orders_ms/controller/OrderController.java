package dev.lumah.orders_ms.controller;

import dev.lumah.orders_ms.dto.request.CreateOrderRequest;
import dev.lumah.orders_ms.dto.response.OrderResponse;
import dev.lumah.orders_ms.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.notification.exchange}")
    private String NOTIFICATION_EXCHANGE;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody @Valid CreateOrderRequest dto) {
        OrderResponse response = orderService.createOrder(dto);
        rabbitTemplate.convertAndSend(NOTIFICATION_EXCHANGE, "email.order.created", response);
        return response;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/send/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse sendOrder(@PathVariable String id) {
        OrderResponse response = orderService.sendOrder(id);
        rabbitTemplate.convertAndSend(NOTIFICATION_EXCHANGE, "email.order.sent", response);
        return response;
    }
}
