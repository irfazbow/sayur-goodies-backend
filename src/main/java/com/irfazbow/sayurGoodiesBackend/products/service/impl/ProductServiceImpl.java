package com.irfazbow.sayurGoodiesBackend.products.service.impl;

import com.irfazbow.sayurGoodiesBackend.products.entity.Product;
import com.irfazbow.sayurGoodiesBackend.products.service.ProductService;
import jakarta.annotation.PostConstruct;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log
public class ProductServiceImpl implements ProductService {

    List<Product> products = new ArrayList<>();

    @PostConstruct
    public void init() {
        Product.Metadata metadata1 = new Product.Metadata("g", 100, 190, 24, 9, 100, 2);
        Product product1 = new Product(0L, 0.0032, 1000, "Beetles", "Exotic", "/products/beetles.png", metadata1);

        Product.Metadata metadata2 = new Product.Metadata("g", 200, 300, 30, 15, 200, 5);
        Product product2 = new Product(1L   , 0.0045, 2000, "Crickets", "Exotic", "/products/crickets.png", metadata2);

        products.add(product1);
        products.add(product2);
    }

    @Override
    public boolean productExists(Long id) {
        return products.stream()
                .anyMatch(product -> product.getId().equals(id));
    }

    private Long generateNewId() {
        List<Long> sortedIds = products.stream()
                .map(Product::getId)
                .sorted()
                .toList();

        long newId = 0;
        for (Long id : sortedIds) {
            if (!id.equals(newId)) {
                break;
            }
            newId++;
        }
        return newId;
    }

    @Override
    public Product createProduct(Product product) {
        if (product.getId() == null) {
            product.setId(generateNewId());
        }
        products.add(product);
        return product;
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> getAllAndSearchProducts() {
        return new ArrayList<>(products);
    }

    @Override
    public Product updateProduct(Product updatedProduct) {
        Product currentProduct = products.stream()
                .filter(product -> Objects.equals(product.getId(), updatedProduct.getId()))
                .findFirst()
                .orElse(null);

        if (currentProduct != null) {
            currentProduct.setName(updatedProduct.getName());
            currentProduct.setCategory(updatedProduct.getCategory());
            currentProduct.setPrice(updatedProduct.getPrice());
            currentProduct.setWeight(updatedProduct.getWeight());
            currentProduct.setImageUrl(updatedProduct.getImageUrl());
            currentProduct.setMetadata(updatedProduct.getMetadata());
        }

        return currentProduct;

    }
    public void deleteProductById(Long id) {
        products.removeIf(product -> product.getId().equals(id));
    }

}
