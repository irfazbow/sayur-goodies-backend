package com.irfazbow.sayurGoodiesBackend.products;

import com.irfazbow.sayurGoodiesBackend.products.entity.Product;
import com.irfazbow.sayurGoodiesBackend.products.service.ProductService;
import com.irfazbow.sayurGoodiesBackend.response.Response;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import javax.swing.text.html.Option;
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
    public ResponseEntity<Response<List<Product>>> getAllAndSearchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category) {
        List<Product> products = productService.getAllAndSearchProducts(name, category);
        return Response.successfulResponse("All products fetched", products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Product>> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Optional<Product> updatedProduct = productService.updateProduct(id, product);
        return updatedProduct.map(value -> Response.successfulResponse("Product has been updated", value)).orElseGet(()
                -> Response.failedResponse(HttpStatus.NOT_FOUND.value(), "Product ID not found", null));
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
