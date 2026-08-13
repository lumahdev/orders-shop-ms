package dev.lumah.orders_ms.service;

import dev.lumah.orders_ms.dto.request.CreateProductRequest;
import dev.lumah.orders_ms.dto.response.ProductResponse;
import dev.lumah.orders_ms.exceptions.InsufficientStockException;
import dev.lumah.orders_ms.exceptions.ProductNotFoundException;
import dev.lumah.orders_ms.model.Product;
import dev.lumah.orders_ms.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private BigDecimal getDiscount(BigDecimal discount) {
        return Objects.requireNonNullElse(discount, BigDecimal.ZERO);
    }

    private Boolean getActive(Boolean active) {
        return Objects.requireNonNullElse(active, true);
    }

    public Product findProduct(String id) {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    public ProductResponse createProduct(CreateProductRequest dto) {
        Product product = new Product();

        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStock(dto.stock());
        product.setActive(getActive(dto.active()));
        product.setDiscount(getDiscount(dto.discount()));

        Product saved = productRepository.save(product);

        return ProductResponse.toDto(saved);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::toDto)
                .toList();
    }

    public ProductResponse getProductById(String id) {
        Product product = findProduct(id);
        return ProductResponse.toDto(product);
    }

    public void deduceStock(String id, Integer quantity) {
        Product product = findProduct(id);

        if (product.getStock() < quantity) {
            throw new InsufficientStockException();
        }

        product.setStock(product.getStock() - quantity);

        productRepository.save(product);
    }
}
