package dev.lumah.orders_ms.controller;

import dev.lumah.orders_ms.dto.request.CreateOrderRequest;
import dev.lumah.orders_ms.dto.response.OrderResponse;
import dev.lumah.orders_ms.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static dev.lumah.orders_ms.config.RabbitMqConfig.ORDER_PAYMENT_PENDING;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody @Valid CreateOrderRequest dto) {
        OrderResponse response = orderService.createOrder(dto);
        rabbitTemplate.convertAndSend(ORDER_PAYMENT_PENDING, response);
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
}
