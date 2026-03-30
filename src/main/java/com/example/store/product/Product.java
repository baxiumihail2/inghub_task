package com.example.store.product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record Product(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        int quantity,
        Instant createdAt,
        Instant updatedAt
) {
    public Product withPrice(BigDecimal newPrice, Instant updateTime) {
        return new Product(id, name, description, newPrice, quantity, createdAt, updateTime);
    }
}
