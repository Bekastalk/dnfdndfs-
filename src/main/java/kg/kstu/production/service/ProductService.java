package kg.kstu.production.service;


import kg.kstu.production.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAll();

    void createProduct(Product product);

    String deleteProduct(Long id);

    Optional<Product> findById(Long id);

    void updateProduct(Product product);

    Optional<Product> getById(Long productId);
}
