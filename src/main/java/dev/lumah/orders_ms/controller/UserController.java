package dev.lumah.orders_ms.controller;

import dev.lumah.orders_ms.dto.request.CreateUserRequest;
import dev.lumah.orders_ms.dto.response.UserResponse;
import dev.lumah.orders_ms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.notification.exchange}")
    private String NOTIFICATION_EXCHANGE;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody @Valid CreateUserRequest dto) {
        UserResponse response = userService.createUser(dto);
        rabbitTemplate.convertAndSend(NOTIFICATION_EXCHANGE, "email.user.validate", response);
        return response;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/validate-email/{id}")
    public UserResponse validateUserEmail(@PathVariable String id) {
        return userService.validateUserEmail(id);
    }
}
