package com.example.store.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.store.product.dto.CreateProductRequest;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

    private ProductService productService;

    @BeforeEach
    void setUp() {
        Clock fixedClock = Clock.fixed(Instant.parse("2026-03-30T10:15:30Z"), ZoneOffset.UTC);
        productService = new ProductService(fixedClock);
    }

    @Test
    void addProductShouldPersistAndReturnProduct() {
        Product created = productService.addProduct(new CreateProductRequest(
                "Laptop",
                "Business laptop",
                new BigDecimal("1299.99"),
                3
        ));

        assertNotNull(created.id());
        assertEquals("Laptop", created.name());
        assertEquals(new BigDecimal("1299.99"), created.price());
        assertEquals(created, productService.findProduct(created.id()));
    }

    @Test
    void changePriceShouldUpdateProductPrice() {
        Product created = productService.addProduct(new CreateProductRequest(
                "Mouse",
                "Wireless mouse",
                new BigDecimal("35.50"),
                10
        ));

        Product updated = productService.changePrice(created.id(), new BigDecimal("39.90"));

        assertEquals(new BigDecimal("39.90"), updated.price());
        assertEquals(created.id(), updated.id());
    }

    @Test
    void findProductShouldThrowWhenMissing() {
        assertThrows(ProductNotFoundException.class, () -> productService.findProduct(java.util.UUID.randomUUID()));
    }
}
