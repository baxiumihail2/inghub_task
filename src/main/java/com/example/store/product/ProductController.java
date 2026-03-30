package com.example.store.product;

import com.example.store.product.dto.ChangePriceRequest;
import com.example.store.product.dto.CreateProductRequest;
import com.example.store.product.dto.ProductResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse addProduct(@Valid @RequestBody CreateProductRequest request) {
        return ProductResponse.from(productService.addProduct(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'VIEWER')")
    public ProductResponse findProduct(@PathVariable UUID id) {
        return ProductResponse.from(productService.findProduct(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'VIEWER')")
    public List<ProductResponse> listProducts() {
        return productService.listProducts().stream()
                .map(ProductResponse::from)
                .toList();
    }

    @PatchMapping("/{id}/price")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse changePrice(@PathVariable UUID id, @Valid @RequestBody ChangePriceRequest request) {
        return ProductResponse.from(productService.changePrice(id, request.price()));
    }
}
