package dev.lumah.orders_ms.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document(collection = "payments")
public class Payment {

    @Id
    private String id;
    private String orderId;
    private String userId;
    private BigDecimal total;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
}
