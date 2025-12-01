package com.tienda.tienda_backend.service;

import com.tienda.tienda_backend.entity.Product;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product createProduct(Product product);
    Product getProductById(Long id);
    Product updateProduct(Long id, Product product); // NUEVO MÉTODO
    void deleteProduct(Long id);
}
