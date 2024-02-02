package kg.kstu.production.service.impl;

import kg.kstu.production.entity.Budget;
import kg.kstu.production.repository.BudgetRepository;
import kg.kstu.production.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {
    private final BudgetRepository budgetRepository;
    @Override
    public List<Budget> getBudget() {
        return budgetRepository.findAll();
    }
}
