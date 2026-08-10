package dev.lumah.orders_ms.service;

import dev.lumah.orders_ms.client.ProductClient;
import dev.lumah.orders_ms.client.dto.ProductResponse;
import dev.lumah.orders_ms.dto.CreateOrderRequest;
import dev.lumah.orders_ms.dto.OrderItemRequest;
import dev.lumah.orders_ms.dto.OrderResponse;
import dev.lumah.orders_ms.exceptions.InsufficientStockException;
import dev.lumah.orders_ms.exceptions.ProductInactiveException;
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

    @Autowired
    private ProductClient productClient;

    public OrderResponse createOrder(CreateOrderRequest dto) {

        List<OrderItem> items = dto.items()
                .stream()
                .map(this::createOrderItem)
                .toList();

        Order order = buildOrder(dto, items);

        Order savedOrder = orderRepository.save(order);

        return OrderResponse.toDto(savedOrder);
    }

    private OrderItem createOrderItem(OrderItemRequest item) {

        ProductResponse product = productClient.getProductById(item.productId());

        validateProduct(product, item.quantity());

        OrderItem orderItem = new OrderItem();

        orderItem.setProductId(product.id());
        orderItem.setQuantity(item.quantity());

        return orderItem;
    }

    private void validateProduct(ProductResponse product, Integer quantity) {

        if (!Boolean.TRUE.equals(product.active())) {
            throw new ProductInactiveException("Product with id " + product.id() + " is inactive");
        }

        if (quantity > product.stock()) {
            throw new InsufficientStockException("Insufficient stock for product " + product.id());
        }
    }

    private Order buildOrder(CreateOrderRequest dto, List<OrderItem> items) {

        Order order = new Order();

        order.setUserId(dto.userId());
        order.setStatus(Status.PAYMENT_PENDING);
        order.setItems(items);

        return order;
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
