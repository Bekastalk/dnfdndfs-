package kg.kstu.production.service;

import kg.kstu.production.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAll();

    void createEmployee(Employee employee);

    void deleteEmployee(Long id);

    Optional<Employee> findById(Long id);

    void updateEmployee (Employee employee);
}
