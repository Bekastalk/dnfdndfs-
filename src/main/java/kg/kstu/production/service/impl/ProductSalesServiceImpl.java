package kg.kstu.production.service.impl;

import kg.kstu.production.entity.Budget;
import kg.kstu.production.entity.Product;
import kg.kstu.production.entity.ProductSale;
import kg.kstu.production.repository.BudgetRepository;
import kg.kstu.production.repository.ProductRepository;
import kg.kstu.production.repository.ProductSalesRepository;
import kg.kstu.production.service.ProductSalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductSalesServiceImpl implements ProductSalesService {
    private final ProductSalesRepository salesRepository;
    private final BudgetRepository budgetRepository;
    private final ProductRepository productRepository;

    @Override
    public List<ProductSale> getAll() {
        return salesRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(ProductSale::getSalesDate))
                .collect(Collectors.toList());
    }

    @Override
    public String create(ProductSale sale) {
        Optional<Product> productOptional = productRepository.findById(sale.getProduct().getId());
        if(productOptional.isPresent()) {
            Product product = productOptional.get();

            if(product.getQuantity() >= sale.getQuantity()) {
                float amount = (product.getAmount() / product.getQuantity()) * sale.getQuantity();
                product.setQuantity(product.getQuantity() - sale.getQuantity());
                // метод для получение бюджета
                Optional<Budget> budgetOptional = budgetRepository.findFirstByOrderByIdAsc();
                product.setAmount(product.getAmount()-amount);
                if (budgetOptional.isPresent()) {
                    Budget budget = budgetOptional.get();
                    sale.setAmount(amount + (amount * (budget.getPercent() / 100)));
                    budget.setAmount(budget.getAmount() + sale.getAmount());

                    productRepository.save(product);
                    budgetRepository.save(budget);
                    salesRepository.save(sale);

                    return "Done";
                }
            }
        }
        return null;
    }
}
