package dev.lumah.orders_ms.service;

import dev.lumah.orders_ms.dto.request.CreateUserRequest;
import dev.lumah.orders_ms.dto.response.UserResponse;
import dev.lumah.orders_ms.exceptions.InactiveUserException;
import dev.lumah.orders_ms.exceptions.UserNotFoundException;
import dev.lumah.orders_ms.model.Address;
import dev.lumah.orders_ms.model.User;
import dev.lumah.orders_ms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLookupService userLookupService;

    private LocalDate getCreationDate(LocalDate date) {
        return Objects.requireNonNullElseGet(date, LocalDate::now);
    }

    private Boolean getActive(Boolean active) {
        return Objects.requireNonNullElse(active, true);
    }

    User findUser(String id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public UserResponse createUser(CreateUserRequest dto) {
        Address address = new Address();
        address.setCep(dto.address().cep());
        address.setStreet(dto.address().street());
        address.setNumber(dto.address().number());
        address.setAdditional(dto.address().additional());
        address.setNeighborhood(dto.address().neighborhood());
        address.setState(dto.address().state());

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setPhone(dto.phone());
        user.setCreationDate(getCreationDate(dto.creationDate()));
        user.setActive(getActive(dto.active()));
        user.setAddress(address);
        user.setEmailValidated(false);
        User savedUser = userRepository.save(user);

        return UserResponse.toDto(savedUser);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::toDto)
                .toList();
    }

    public UserResponse getUserById(String id) {
        return UserResponse.toDto(findUser(id));
    }

    public UserResponse validateUserEmail(String id) {
        User user = userLookupService.findUser(id);

        if (!Boolean.TRUE.equals(user.getActive())) {
            throw new InactiveUserException();
        }

        user.setEmailValidated(true);
        userRepository.save(user);
        return UserResponse.toDto(user);
    }
}
