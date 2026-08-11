package dev.lumah.orders_ms.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Data
@Document(collection = "orders")
public class Order {

    @Id
    private String id;
    private String userId;
    private OrderStatus status;
    private List<OrderItem> items;
    private BigDecimal total;
    private BigDecimal discount;
    private String userMail;
    private String userName;
    private String userPhone;
    private Address userAddress;
}
