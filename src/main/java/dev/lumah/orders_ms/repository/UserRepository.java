package dev.lumah.orders_ms.repository;

import dev.lumah.orders_ms.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
