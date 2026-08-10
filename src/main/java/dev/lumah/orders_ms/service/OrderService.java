package dev.lumah.orders_ms.service;

import dev.lumah.orders_ms.client.ProductClient;
import dev.lumah.orders_ms.client.UserClient;
import dev.lumah.orders_ms.client.dto.ProductResponse;
import dev.lumah.orders_ms.client.dto.UserResponse;
import dev.lumah.orders_ms.dto.CreateOrderRequest;
import dev.lumah.orders_ms.dto.OrderItemRequest;
import dev.lumah.orders_ms.dto.OrderResponse;
import dev.lumah.orders_ms.exceptions.BusinessException;
import dev.lumah.orders_ms.model.Order;
import dev.lumah.orders_ms.model.OrderItem;
import dev.lumah.orders_ms.model.Status;
import dev.lumah.orders_ms.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserClient userClient;

    public OrderResponse createOrder(CreateOrderRequest dto) {

        List<OrderItem> items = dto.items()
                .stream()
                .map(this::createOrderItem)
                .toList();

        BigDecimal total = calculateTotal(items, dto.discount());

        Order order = buildOrder(dto, items, total);

        Order savedOrder = orderRepository.save(order);

        return OrderResponse.toDto(savedOrder);
    }

    private OrderItem createOrderItem(OrderItemRequest item) {

        ProductResponse product =
                productClient.getProductById(item.productId());

        validateProduct(product, item.quantity());

        OrderItem orderItem = new OrderItem();

        orderItem.setProductId(product.id());
        orderItem.setName(product.name());
        orderItem.setDescription(product.description());
        orderItem.setPrice(product.price());
        orderItem.setDiscount(product.discount());
        orderItem.setQuantity(item.quantity());

        return orderItem;
    }

    private void validateProduct(ProductResponse product, Integer quantity) {

        if (!Boolean.TRUE.equals(product.active())) {
            throw new BusinessException("Produto com id " + product.id() + " inválido.");
        }

        if (quantity > product.stock()) {
            throw new BusinessException("Estoque para o produto " + product.id() + " inválido.");
        }
    }

    private BigDecimal calculateTotal(List<OrderItem> items, BigDecimal orderDiscount) {

        BigDecimal itemsTotal = items.stream()
                .map(item -> {
                    BigDecimal discount = item.getDiscount().divide(BigDecimal.valueOf(100));

                    BigDecimal priceWithDiscount = item.getPrice().multiply(BigDecimal.ONE.subtract(discount));

                    return priceWithDiscount.multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal discount = orderDiscount.divide(BigDecimal.valueOf(100));

        return itemsTotal.multiply(BigDecimal.ONE.subtract(discount));
    }

    private Order buildOrder(CreateOrderRequest dto, List<OrderItem> items, BigDecimal total) {

        UserResponse user = userClient.getUserById(dto.userId());

        if (!Boolean.TRUE.equals(user.active())) {
            throw new BusinessException("Usuário com id " + user.id() + " inválido.");
        }

        Order order = new Order();

        order.setUserId(user.id());
        order.setStatus(Status.PAYMENT_PENDING);
        order.setItems(items);
        order.setTotal(total);
        order.setDiscount(dto.discount());

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
