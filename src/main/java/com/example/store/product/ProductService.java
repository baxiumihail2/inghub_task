package com.example.store.product;

import com.example.store.product.dto.CreateProductRequest;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final Map<UUID, Product> products = new ConcurrentHashMap<>();
    private final Clock clock;

    public ProductService() {
        this(Clock.systemUTC());
    }

    ProductService(Clock clock) {
        this.clock = clock;
    }

    public Product addProduct(CreateProductRequest request) {
        Instant now = Instant.now(clock);
        Product product = new Product(
                UUID.randomUUID(),
                request.name(),
                request.description(),
                request.price(),
                request.quantity(),
                now,
                now
        );
        products.put(product.id(), product);
        log.info("Created product id={} name={} price={}", product.id(), product.name(), product.price());
        return product;
    }

    public Product findProduct(UUID id) {
        Product product = products.get(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        return product;
    }

    public List<Product> listProducts() {
        return products.values().stream()
                .sorted(Comparator.comparing(Product::createdAt))
                .toList();
    }

    public Product changePrice(UUID id, BigDecimal newPrice) {
        Product updated = products.compute(id, (productId, current) -> {
            if (current == null) {
                return null;
            }
            return current.withPrice(newPrice, Instant.now(clock));
        });

        if (updated == null) {
            throw new ProductNotFoundException(id);
        }

        log.info("Updated price for product id={} newPrice={}", id, newPrice);
        return updated;
    }
}
