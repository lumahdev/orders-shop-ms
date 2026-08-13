package dev.lumah.orders_ms.service;

import dev.lumah.orders_ms.dto.request.CreateOrderRequest;
import dev.lumah.orders_ms.dto.request.CreateOrderItemRequest;
import dev.lumah.orders_ms.dto.response.OrderResponse;
import dev.lumah.orders_ms.exceptions.*;
import dev.lumah.orders_ms.model.*;
import dev.lumah.orders_ms.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    private BigDecimal validateOrderDiscount(BigDecimal discount) {
        return Objects.requireNonNullElse(discount, BigDecimal.ZERO);
    }

    private Order findOrder(String id) {
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    private OrderItem createOrderItem(CreateOrderItemRequest item) {
        Product product = validateProduct(item.productId(), item.quantity());

        OrderItem orderItem = new OrderItem();

        orderItem.setProductId(product.getId());
        orderItem.setName(product.getName());
        orderItem.setPrice(product.getPrice());
        orderItem.setDiscount(product.getDiscount());
        orderItem.setQuantity(item.quantity());

        return orderItem;
    }

    private BigDecimal calculateTotal(List<OrderItem> items) {

        return items.stream()
                .map(item -> {

                    BigDecimal discount = validateOrderDiscount(item.getDiscount());

                    BigDecimal discountRate = discount.divide(BigDecimal.valueOf(100));

                    BigDecimal priceWithDiscount = item.getPrice().multiply(BigDecimal.ONE.subtract(discountRate));

                    return priceWithDiscount.multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateDiscount(List<OrderItem> items) {

        return items.stream()
                .map(item -> {

                    BigDecimal discount = validateOrderDiscount(item.getDiscount());

                    BigDecimal itemTotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

                    return itemTotal
                            .multiply(discount)
                            .divide(BigDecimal.valueOf(100));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Order buildOrder(
            CreateOrderRequest dto,
            List<OrderItem> items,
            BigDecimal total,
            BigDecimal discount) {

        User user = validateUser(dto.userId());

        Order order = new Order();

        order.setUserId(user.getId());
        order.setStatus(OrderStatus.PAYMENT_PENDING);
        order.setItems(items);
        order.setTotal(total);
        order.setDiscount(discount);
        order.setUserMail(user.getEmail());
        order.setUserName(user.getName());
        order.setUserPhone(user.getPhone());
        order.setUserAddress(user.getAddress());

        return order;
    }

    public OrderResponse createOrder(CreateOrderRequest dto) {

        List<OrderItem> items = dto.items()
                .stream()
                .map(this::createOrderItem)
                .toList();

        BigDecimal total = calculateTotal(items);
        BigDecimal discount = calculateDiscount(items);

        Order order = buildOrder(dto, items, total, discount);

        Order savedOrder = orderRepository.save(order);

        return OrderResponse.toDto(savedOrder);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponse::toDto)
                .toList();
    }

    public OrderResponse getOrderById(String id) {
        return OrderResponse.toDto(findOrder(id));
    }

    public void changeOrderStatus(String id, OrderStatus status) {
        Order order = validateOrder(id);
        order.setStatus(status);
        orderRepository.save(order);
    }
}
