package kg.kstu.production.service;

import kg.kstu.production.entity.Salary;

import java.util.List;

public interface SalaryService {
    List<Salary> getAll(Integer year, Integer month);
}
