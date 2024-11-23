package tech.solutionhut.ecommerce.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.solutionhut.ecommerce.product.domain.Product;
import tech.solutionhut.ecommerce.product.record.ProductPurchaseRequest;
import tech.solutionhut.ecommerce.product.record.ProductPurchaseResponse;
import tech.solutionhut.ecommerce.product.record.ProductRequest;
import tech.solutionhut.ecommerce.product.record.ProductResponse;
import tech.solutionhut.ecommerce.product.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Integer> createProduct(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(service.createProduct(request));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProduct(@Valid @RequestBody List<ProductPurchaseRequest> requests) {
        return ResponseEntity.ok(service.purchaseProduct(requests));
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable("product-id") Integer productId) {
        return ResponseEntity.ok(service.findById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
}
