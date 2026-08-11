package dev.lumah.orders_ms.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal discount;
    private Integer stock;
    private Boolean active;
}
