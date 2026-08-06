package dev.lumah.orders_ms.service;

import dev.lumah.orders_ms.dto.CreateOrderRequest;
import dev.lumah.orders_ms.dto.OrderItemRequest;
import dev.lumah.orders_ms.dto.OrderResponse;
import dev.lumah.orders_ms.model.Order;
import dev.lumah.orders_ms.model.OrderItem;
import dev.lumah.orders_ms.model.Status;
import dev.lumah.orders_ms.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Captor
    ArgumentCaptor<Order> orderCaptor;

    @DisplayName("Quando cadastrar pediddo")
    @Nested
    class CreateOrder {

        @DisplayName("Então deve cadastrar com sucesso")
        @Nested
        class Sucesso {

            @Test
            @DisplayName("Dado um pedido válido")
            void createOrder() {

                // Arrange
                OrderItemRequest orderItem1 = new OrderItemRequest("1", 5);
                OrderItemRequest orderItem2 = new OrderItemRequest("2", 3);

                CreateOrderRequest request = new CreateOrderRequest(
                        "1",
                        List.of(orderItem1, orderItem2)
                );

                Order savedOrder = new Order();
                savedOrder.setId("123");
                savedOrder.setUserId(request.userId());
                savedOrder.setStatus(Status.PAYMENT_PENDING);
                savedOrder.setItems(
                        request.items()
                                .stream()
                                .map(OrderItemRequest::toEntity)
                                .toList()
                );

                when(orderRepository.save(any(Order.class)))
                        .thenReturn(savedOrder);

                // Act
                OrderResponse response = orderService.createOrder(request);

                // Assert
                assertThat(response).isNotNull();
                assertThat(response.id()).isNotBlank();
                assertThat(response.userId()).isEqualTo(request.userId());
                assertThat(response.status()).isEqualTo(Status.PAYMENT_PENDING);

                assertThat(response.items()).hasSize(2);

                assertThat(response.items().get(0).getProductId()).isEqualTo("1");
                assertThat(response.items().get(0).getQuantity()).isEqualTo(5);

                assertThat(response.items().get(1).getProductId()).isEqualTo("2");
                assertThat(response.items().get(1).getQuantity()).isEqualTo(3);

                verify(orderRepository).save(any(Order.class));
                System.out.println(response);
            }

//            @Test
//            @DisplayName("Dado creationDate for null")
//            void creationDateNull() {
//                CreateAddressRequest address = new CreateAddressRequest("21321987", "Rua das Palmeiras", "32", "Casa 23", "Praça Molhada", "Paraná");
//                CreateOrderRequest request = new CreateOrderRequest("Josefino", "josefino@gmail.com", "Senha@123", "21987878909", address, null, true);
//
//                Address savedAddress = new Address();
//                savedAddress.setId("123");
//                savedAddress.setCep(address.cep());
//                savedAddress.setStreet(address.street());
//                savedAddress.setNumber(address.number());
//                savedAddress.setAdditional(address.additional());
//                savedAddress.setNeighborhood(address.neighborhood());
//                savedAddress.setState(address.state());
//
//                Order savedOrder = new Order();
//                savedOrder.setId("123");
//                savedOrder.setName(request.name());
//                savedOrder.setEmail(request.email());
//                savedOrder.setPassword(request.password());
//                savedOrder.setPhone(request.phone());
//                savedOrder.setAddress(savedAddress);
//                savedOrder.setCreationDate(request.creationDate());
//                savedOrder.setActive(request.active());
//
//                when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
//
//                OrderResponse response = orderService.createOrder(request);
//
//                verify(orderRepository).save(orderCaptor.capture());
//
//                Order order = orderCaptor.getValue();
//
//                assertThat(order.getCreationDate()).isEqualTo(LocalDate.now());
//                System.out.println(order);
//            }
        }
    }
}