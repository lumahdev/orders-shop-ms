package dev.lumah.orders_ms.client;

import dev.lumah.orders_ms.client.dto.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UserClient {

    private final WebClient webClient;

    @Value("${services.users.url}")
    private String usersUrl;

    public UserClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public UserResponse getUserById(String userId) {

        return webClient
                .get()
                .uri(usersUrl + "/users/" + userId)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block();
    }
}
