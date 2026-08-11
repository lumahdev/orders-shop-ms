package dev.lumah.orders_ms.repository;

import dev.lumah.orders_ms.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<Address, String> {
}
