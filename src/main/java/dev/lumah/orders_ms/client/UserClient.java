package dev.lumah.orders_ms.client;

import dev.lumah.orders_ms.client.dto.UserResponse;
import dev.lumah.orders_ms.exceptions.RemoteServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
                .onStatus(
                        HttpStatusCode::isError,
                        response -> response
                                .bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new RemoteServiceException(response.statusCode(), body)))
                )
                .bodyToMono(UserResponse.class)
                .block();
    }
}
