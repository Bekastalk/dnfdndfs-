package kg.kstu.production.service.impl;

import kg.kstu.production.entity.Salary;
import kg.kstu.production.repository.SalaryRepository;
import kg.kstu.production.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalaryServiceImpl implements SalaryService {
    private final SalaryRepository salaryRepository;

    public List<Salary> getAll(Integer year, Integer month) {
        return salaryRepository.findAllByYearAndMonth(year, month)
                .stream()
                .sorted(Comparator.comparing(s -> s.getEmployee().getName()))
                .collect(Collectors.toList());
    }


}