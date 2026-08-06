package dev.lumah.orders_ms.service;

import dev.lumah.orders_ms.dto.CreateOrderRequest;
import dev.lumah.orders_ms.dto.OrderResponse;
import dev.lumah.orders_ms.model.Order;
import dev.lumah.orders_ms.model.OrderItem;
import dev.lumah.orders_ms.model.Status;
import dev.lumah.orders_ms.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderResponse createOrder(CreateOrderRequest dto) {
        Order order = new Order();

        order.setUserId(dto.userId());
        order.setStatus(Status.PAYMENT_PENDING);

        List<OrderItem> items = dto.items()
                .stream()
                .map(item -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProductId(item.productId());
                    orderItem.setQuantity(item.quantity());
                    return orderItem;
                })
                .toList();

        order.setItems(items);

        Order saved = orderRepository.save(order);

        return OrderResponse.toDto(saved);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponse::toDto)
                .toList();
    }

    public OrderResponse getOrderById(String id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado."));
        return OrderResponse.toDto(order);
    }
}
