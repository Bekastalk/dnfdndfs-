package kg.kstu.production.repository;

import kg.kstu.production.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByAmountGreaterThan(Float amount);
}
