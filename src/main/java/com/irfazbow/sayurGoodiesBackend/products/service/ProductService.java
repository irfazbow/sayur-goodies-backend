package com.irfazbow.sayurGoodiesBackend.products.service;

import com.irfazbow.sayurGoodiesBackend.products.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public Product createProduct(Product product);
    public Optional<Product> getProductById(Long id);
    public List<Product> getAllAndSearchProducts();
    public Product updateProduct(Product updatedProduct);
    public void deleteProductById(Long id);
    public boolean productExists(Long id);
}
