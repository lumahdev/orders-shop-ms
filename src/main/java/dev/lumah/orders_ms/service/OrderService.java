package dev.lumah.orders_ms.service;

import dev.lumah.orders_ms.dto.request.CreateOrderRequest;
import dev.lumah.orders_ms.dto.request.CreateOrderItemRequest;
import dev.lumah.orders_ms.dto.response.OrderResponse;
import dev.lumah.orders_ms.dto.response.ProductResponse;
import dev.lumah.orders_ms.dto.response.UserResponse;
import dev.lumah.orders_ms.exceptions.BusinessException;
import dev.lumah.orders_ms.exceptions.ProductNotFoundException;
import dev.lumah.orders_ms.exceptions.UserNotFoundException;
import dev.lumah.orders_ms.model.Order;
import dev.lumah.orders_ms.model.OrderItem;
import dev.lumah.orders_ms.model.Status;
import dev.lumah.orders_ms.repository.OrderRepository;
import dev.lumah.orders_ms.repository.ProductRepository;
import dev.lumah.orders_ms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private BigDecimal validateOrderDiscount(BigDecimal discount) {
        return Objects.requireNonNullElse(discount, BigDecimal.ZERO);
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

    private OrderItem createOrderItem(CreateOrderItemRequest item) {

        ProductResponse product = ProductResponse.toDto(
                productRepository.findById(item.productId()).orElseThrow(() -> new ProductNotFoundException("Product not found"))
        );

        validateProduct(product, item.quantity());

        OrderItem orderItem = new OrderItem();

        orderItem.setProductId(product.id());
        orderItem.setName(product.name());
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

        UserResponse user = UserResponse.toDto(
                userRepository.findById(dto.userId()).orElseThrow(() -> new UserNotFoundException("User not found"))
        );

        if (!Boolean.TRUE.equals(user.active())) {
            throw new BusinessException("Usuário com id " + user.id() + " inválido.");
        }

        Order order = new Order();

        order.setUserId(user.id());
        order.setStatus(Status.PAYMENT_PENDING);
        order.setItems(items);
        order.setTotal(total);
        order.setDiscount(discount);
        order.setUserMail(user.email());
        order.setUserName(user.name());
        order.setUserPhone(user.phone());
        order.setUserAddress(user.address());

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
