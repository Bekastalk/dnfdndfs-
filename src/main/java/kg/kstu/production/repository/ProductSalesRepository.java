package kg.kstu.production.repository;

import kg.kstu.production.entity.ProductSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSalesRepository extends JpaRepository<ProductSale, Long> {
}
