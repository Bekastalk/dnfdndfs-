package kg.kstu.production.service.impl;

import kg.kstu.production.entity.Budget;
import kg.kstu.production.entity.Material;
import kg.kstu.production.entity.MaterialPurchase;
import kg.kstu.production.repository.BudgetRepository;
import kg.kstu.production.repository.MaterialPurchaseRepository;
import kg.kstu.production.repository.MaterialRepository;
import kg.kstu.production.service.MaterialPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterialPurchaseServiceImpl implements MaterialPurchaseService {
    private final MaterialPurchaseRepository purchaseRepository;
    private final BudgetRepository budgetRepository;
    private final MaterialRepository materialRepository;

    @Override
    public List<MaterialPurchase> getAll() {
        return purchaseRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(MaterialPurchase::getPurchaseDate))
                .collect(Collectors.toList());
    }

    @Override
    public String create(MaterialPurchase materialPurchase) {
        Optional<Budget> budget = budgetRepository.findByAmountGreaterThan(materialPurchase.getAmount());
        if (budget.isPresent()) {
            purchaseRepository.save(materialPurchase);

            Optional<Material> material =  materialRepository.findById(materialPurchase.getMaterial().getId());
            if (material.isPresent()) {
                material.get().setQuantity(material.get().getQuantity() + materialPurchase.getQuantity());
                material.get().setAmount(material.get().getAmount() + materialPurchase.getAmount());

                Material updatedMaterial = material.get();
                materialRepository.save(updatedMaterial);
            }

            budget.get().setAmount(budget.get().getAmount() - materialPurchase.getAmount());
            Budget updatedBudget = budget.get();
            budgetRepository.save(updatedBudget);
            return "Done";
        }
        return "null";
    }
}
