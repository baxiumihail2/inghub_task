package com.example.store.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.store.product.dto.ChangePriceRequest;
import com.example.store.product.dto.CreateProductRequest;
import com.example.store.product.dto.ProductResponse;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductControllerTest {

    private ProductService productService;
    private ProductController productController;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);
    }

    @Test
    void addProductShouldReturnMappedResponse() {
        CreateProductRequest request = new CreateProductRequest(
                "Laptop",
                "Business laptop",
                new BigDecimal("1299.99"),
                5
        );
        Product product = product(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "Laptop",
                "Business laptop",
                new BigDecimal("1299.99"),
                5
        );
        when(productService.addProduct(request)).thenReturn(product);

        ProductResponse response = productController.addProduct(request);

        assertEquals(ProductResponse.from(product), response);
        verify(productService).addProduct(request);
    }

    @Test
    void findProductShouldReturnMappedResponse() {
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");
        Product product = product(
                id,
                "Laptop",
                "Business laptop",
                new BigDecimal("1299.99"),
                5
        );
        when(productService.findProduct(id)).thenReturn(product);

        ProductResponse response = productController.findProduct(id);

        assertEquals(ProductResponse.from(product), response);
        verify(productService).findProduct(id);
    }

    @Test
    void listProductsShouldReturnMappedResponses() {
        Product first = product(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "Laptop",
                "Business laptop",
                new BigDecimal("1299.99"),
                5
        );
        Product second = product(
                UUID.fromString("22222222-2222-2222-2222-222222222222"),
                "Mouse",
                "Wireless mouse",
                new BigDecimal("25.50"),
                20
        );
        when(productService.listProducts()).thenReturn(List.of(first, second));

        List<ProductResponse> response = productController.listProducts();

        assertIterableEquals(List.of(ProductResponse.from(first), ProductResponse.from(second)), response);
        verify(productService).listProducts();
    }

    @Test
    void changePriceShouldReturnMappedResponse() {
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");
        ChangePriceRequest request = new ChangePriceRequest(new BigDecimal("1399.99"));
        Product updated = product(
                id,
                "Laptop",
                "Business laptop",
                new BigDecimal("1399.99"),
                5
        );
        when(productService.changePrice(id, request.price())).thenReturn(updated);

        ProductResponse response = productController.changePrice(id, request);

        assertEquals(ProductResponse.from(updated), response);
        verify(productService).changePrice(id, request.price());
    }

    private Product product(UUID id, String name, String description, BigDecimal price, int quantity) {
        Instant timestamp = Instant.parse("2026-03-31T09:00:00Z");
        return new Product(id, name, description, price, quantity, timestamp, timestamp);
    }
}
