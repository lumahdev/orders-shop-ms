package dev.lumah.orders_ms.repository;

import dev.lumah.orders_ms.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment, String> { }
