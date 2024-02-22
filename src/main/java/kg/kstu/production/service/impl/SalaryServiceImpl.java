package kg.kstu.production.service.impl;

import kg.kstu.production.entity.Budget;
import kg.kstu.production.entity.Employee;
import kg.kstu.production.entity.Salary;
import kg.kstu.production.repository.*;
import kg.kstu.production.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {
    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;
    private final MaterialPurchaseRepository purchaseRepository;
    private final ProductProductionRepository productionRepository;
    private final ProductSalesRepository salesRepository;
    private final BudgetRepository budgetRepository;

    @Override
    public List<Salary> getAll(Integer year, Integer month) {
        return salaryRepository.findAllByYearAndMonth(year, month)
                .stream()
                .sorted(Comparator.comparing(s -> s.getEmployee().getName()))
                .collect(Collectors.toList());
    }

    public String createSalaryForYearAndMonth(Integer year, Integer month) {
        if (salaryRepository.findFirstByYearAndMonth(year, month)) {
            return "Salary for this year and month alrady exists";
        } else {
            Budget budget = budgetRepository.findFirstByOrderByIdAsc().get();
            List<Employee> employeeList = employeeRepository.findAll();
            for (Employee employee : employeeList) {
                //ToDo add filtration for operations by Year and Month
                Integer purchaseCount = purchaseRepository.countByEmployee(employee);
                Integer productionCount = productionRepository.countByEmployee(employee);
                Integer salesCount = salesRepository.countByEmployee(employee);

                Integer common = purchaseCount + productionCount + salesCount;
                Salary salary = Salary.builder()
                        .year(year)
                        .month(month)
                        .employee(employee)
                        .purchase(purchaseCount)
                        .production(productionCount)
                        .sale(salesCount)
                        .common(common)
                        .salary(employee.getSalary())
                        .bonus(budget.getBonus())
                        .general(employee.getSalary() + (common * budget.getBonus()))
                        .status(false)
                        .build();
                salaryRepository.save(salary);
            }
            return "Done";
        }
    }

    public String giveSalary(Integer year, Integer month) {
        Optional<Budget> budget = budgetRepository.findFirstByOrderByIdAsc();
        return "null";
    }
}