package kg.kstu.production.service;

import jakarta.transaction.Transactional;
import kg.kstu.production.entity.Material;

import java.util.List;
import java.util.Optional;

public interface MaterialService {
    List<Material> getAll();

    Optional<Material> findById(Long id);

    String createMaterial(Material material);

    @Transactional
    String deleteMaterial(Long id);

    String updateMaterial(Material material);
}
