package com.irfazbow.sayurGoodiesBackend.products.service;

import com.irfazbow.sayurGoodiesBackend.products.entity.Product;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    public Product createProduct(Product product);
    public Optional<Product> getProductById(Long id);
    public List<Product> getAllAndSearchProducts(String name, String category);
    public Optional<Product> updateProduct(Long id, Product product);
    public void deleteProductById(Long id);
    public boolean productExists(Long id);
}
