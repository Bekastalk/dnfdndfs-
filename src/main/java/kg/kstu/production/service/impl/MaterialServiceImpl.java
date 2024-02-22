package kg.kstu.production.service.impl;

import jakarta.transaction.Transactional;
import kg.kstu.production.entity.Material;
import kg.kstu.production.entity.MaterialPurchase;
import kg.kstu.production.repository.IngredientsRepository;
import kg.kstu.production.repository.MaterialPurchaseRepository;
import kg.kstu.production.repository.MaterialRepository;
import kg.kstu.production.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {
    final MaterialRepository materialRepository;
    final MaterialPurchaseRepository materialPurchaseRepository;
    final IngredientsRepository ingredientsRepository;

    @Override
    public List<Material> getAll() {
        return materialRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Material::getName))
                .collect(Collectors.toList());
    }


    @Override
    public Optional<Material> findById(Long id) {
        return materialRepository.findById(id);
    }

    @Override
    public String createMaterial(Material material) {
        Boolean contains = materialRepository.findAll()
                        .stream()
                                .anyMatch(m -> m.getName().equals(material.getName()));
        if(contains) {
            return "Material already exists";
        } else {
            materialRepository.save(material);
            return "Done";
        }
    }

    @Override
    @Transactional
    public String deleteMaterial(Long id) {
        Optional<Material> material = materialRepository.findById(id);
        if(material.isPresent()) {
            Material newMaterial = material.get();
            materialPurchaseRepository.deleteAllByMaterial(newMaterial);
            ingredientsRepository.deleteAllByMaterial(material);
            materialRepository.deleteById(id);
            return "Done";
        } else {
            return "Error";
        }
    }

    @Override
    public String updateMaterial(Material material) {
        boolean contains = materialRepository.findAll()
                .stream()
                .anyMatch(m -> Objects.equals(m.getName(), material.getName()));
        if (!contains) {
            materialRepository.save(material);
            return "The product added";
        } else {
            Optional<Material> materialToUpdateOptional = materialRepository.findById(material.getId());
            if (materialToUpdateOptional.isEmpty()) {
                return "Ingredient not found";
            } else {
                Material materialToUpdate = materialToUpdateOptional.get();
                materialToUpdate.setQuantity(material.getQuantity());
                materialToUpdate.setAmount(material.getAmount());
                materialToUpdate.setUnitOfMeasurement(material.getUnitOfMeasurement());
                materialRepository.save(materialToUpdate);
                return "The product already contains";
            }
        }
    }

}
