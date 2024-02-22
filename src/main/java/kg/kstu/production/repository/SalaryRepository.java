package kg.kstu.production.repository;

import kg.kstu.production.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    public List<Salary> findAllByYearAndMonth(Integer year, Integer month);
    public Boolean findFirstByYearAndMonth(Integer year, Integer month);
}
