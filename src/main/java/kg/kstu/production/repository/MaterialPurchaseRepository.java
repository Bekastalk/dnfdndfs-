package kg.kstu.production.repository;

import kg.kstu.production.entity.Employee;
import kg.kstu.production.entity.Material;
import kg.kstu.production.entity.MaterialPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialPurchaseRepository extends JpaRepository<MaterialPurchase, Long> {
    public Integer countByEmployee(Employee employee);
    void deleteAllByMaterial(Material material);
}
