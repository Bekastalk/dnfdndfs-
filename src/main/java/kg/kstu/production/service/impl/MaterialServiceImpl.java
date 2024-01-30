package kg.kstu.production.service.impl;

import kg.kstu.production.entity.Material;
import kg.kstu.production.repository.MaterialRepository;
import kg.kstu.production.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {
    final MaterialRepository materialRepository;

    @Override
    public List<Material> getAll() {
        return materialRepository.findAll();
    }
}
