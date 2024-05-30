package com.irfazbow.sayurGoodiesBackend.products;

import com.irfazbow.sayurGoodiesBackend.products.entity.Product;
import com.irfazbow.sayurGoodiesBackend.products.service.ProductService;
import com.irfazbow.sayurGoodiesBackend.response.Response;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@Log
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Response<Product>> createProduct(@Valid @RequestBody Product product) {
        if (productService.productExists(product.getId())) {
            return Response.failedResponse(HttpStatus.CONFLICT.value(), "Product ID already exists", null);
        }
        return Response.successfulResponse(HttpStatus.CREATED.value(),"New product created", productService.createProduct(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Optional<Product>>> getProductById(@PathVariable Long id) {
        var product = productService.getProductById(id);
        if (product.isEmpty()) {
            return Response.failedResponse(HttpStatus.NOT_FOUND.value(), "Product not found", product);
        }
        return Response.successfulResponse("Product found", product);
    }

    @GetMapping
    public ResponseEntity<Response<List<Product>>> getAllAndSearchProducts() {
        return Response.successfulResponse("All products fetched", productService.getAllAndSearchProducts());
    }

    @PutMapping
    public ResponseEntity<Response<Product>> updateProduct(@Valid @RequestBody Product product) {
        return Response.successfulResponse("Product has been updated", productService.updateProduct(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteProductById(@PathVariable Long id) {
        if (!productService.productExists(id)) {
            return Response.failedResponse(HttpStatus.NOT_FOUND.value(), "Product not found", null);
        }
        productService.deleteProductById(id);
        return Response.successfulResponse(HttpStatus.NO_CONTENT.value(), "Product deleted successfully", null);
    }
}
