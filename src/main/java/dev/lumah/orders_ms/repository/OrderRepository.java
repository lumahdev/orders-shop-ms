package dev.lumah.orders_ms.repository;

import dev.lumah.orders_ms.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> { }
