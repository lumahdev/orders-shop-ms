package dev.lumah.orders_ms.client;

import dev.lumah.orders_ms.client.dto.ProductResponse;
import dev.lumah.orders_ms.exceptions.RemoteServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProductClient {

    private final WebClient webClient;

    @Value("${services.products.url}")
    private String productsUrl;

    public ProductClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public ProductResponse getProductById(String productId) {

        return webClient
                .get()
                .uri(productsUrl + "/products/" + productId)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        response -> response
                                .bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new RemoteServiceException(response.statusCode(), body)))
                )
                .bodyToMono(ProductResponse.class)
                .block();
    }
}
