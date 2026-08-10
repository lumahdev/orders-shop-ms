package dev.lumah.orders_ms.client;

import dev.lumah.orders_ms.client.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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
                .bodyToMono(ProductResponse.class)
                .block();
    }
}
