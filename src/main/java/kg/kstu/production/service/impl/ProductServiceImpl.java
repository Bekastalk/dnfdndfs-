package kg.kstu.production.service.impl;

import jakarta.transaction.Transactional;
import kg.kstu.production.entity.Ingredient;
import kg.kstu.production.entity.Product;
import kg.kstu.production.repository.IngredientsRepository;
import kg.kstu.production.repository.ProductRepository;
import kg.kstu.production.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private IngredientsRepository ingredientsRepository;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    @Transactional
    public String deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
        ingredientsRepository.deleteAllByProduct(product);
        productRepository.deleteById(id);
        return "Done";
        } else {
            return "Error";
        }
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
