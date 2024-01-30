package kg.kstu.production.service.impl;

import kg.kstu.production.entity.Product;
import kg.kstu.production.repository.ProductRepository;
import kg.kstu.production.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Optional<Product> getById(Long productId) {
        return productRepository.findById(productId);
    }
    /*private ProductDto mapToProductsDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name((product.getName()))
                .unitOfMeasurement(product.getUnitOfMeasurement())
                .quantity(product.getQuantity())
                .amount(product.getAmount())
                .build();
    }*/
}
