package kg.kstu.production.repository;

import kg.kstu.production.entity.ProductProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductProductionRepository extends JpaRepository<ProductProduction, Long> {
}
