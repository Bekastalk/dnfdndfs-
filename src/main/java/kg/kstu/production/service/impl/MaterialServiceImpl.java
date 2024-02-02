package kg.kstu.production.service.impl;

import kg.kstu.production.entity.Employee;
import kg.kstu.production.entity.Material;
import kg.kstu.production.repository.MaterialRepository;
import kg.kstu.production.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {
    final MaterialRepository materialRepository;

    @Override
    public List<Material> getAll() {
        return materialRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Material::getName))
                .collect(Collectors.toList());
    }
}
