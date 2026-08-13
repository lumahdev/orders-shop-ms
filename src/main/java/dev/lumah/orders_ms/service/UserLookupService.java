package dev.lumah.orders_ms.service;

import dev.lumah.orders_ms.exceptions.UserNotFoundException;
import dev.lumah.orders_ms.model.User;
import dev.lumah.orders_ms.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserLookupService {

    private final UserRepository userRepository;

    public UserLookupService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUser(String id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
