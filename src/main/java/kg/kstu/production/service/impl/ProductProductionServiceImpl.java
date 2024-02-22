package kg.kstu.production.service.impl;

import kg.kstu.production.entity.Ingredient;
import kg.kstu.production.entity.Material;
import kg.kstu.production.entity.Product;
import kg.kstu.production.entity.ProductProduction;
import kg.kstu.production.repository.IngredientsRepository;
import kg.kstu.production.repository.MaterialRepository;
import kg.kstu.production.repository.ProductProductionRepository;
import kg.kstu.production.repository.ProductRepository;
import kg.kstu.production.service.ProductProductionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductProductionServiceImpl implements ProductProductionService {
    private final ProductProductionRepository productionRepository;
    private final IngredientsRepository ingredientsRepository;
    private final MaterialRepository materialRepository;
    private final ProductRepository productRepository;

    @Override
    public List<ProductProduction> getAll() {
        return productionRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(ProductProduction::getPDate))
                .collect(Collectors.toList());
    }

    @Override
    public String create(ProductProduction productProduction) {
        List<Ingredient> ingredientList = getIngredientsOfProduct(productProduction.getProduct().getId(), productProduction.getQuantity());
        Boolean checker = checkMaterialQuantity(ingredientList);
        if (checker.equals(true)) {
            increaseProductQuantity(productProduction.getProduct().getId(), productProduction.getQuantity());
            Float amount = getMaterialAmount(ingredientList);
            productProduction.setAmount(amount);
            productionRepository.save(productProduction);
            return "Done";
        } else {
            return "Не достаточно материалов";
        }
    }

    @Override
    public List<Ingredient> getIngredientsOfProduct(Long productId, Float productQuantity) {
        List<Ingredient> ingredientList = ingredientsRepository.findAll()
                .stream()
                .filter(i -> productId.equals(i.getProduct().getId()))
                //.sorted(Comparator.comparing(i -> i.getMaterial().getName()))
                .collect(Collectors.toList());
        ingredientList.forEach(i -> i.setQuantity(i.getQuantity() * productQuantity));
        return ingredientList;
    }

    @Override
    public Boolean checkMaterialQuantity(List<Ingredient> ingredientListOfTheProduct) {
        List<Material> materials = new ArrayList<>();
        for (Ingredient ingredient : ingredientListOfTheProduct) {
            Optional<Material> materialOptional = materialRepository.findById(ingredient.getMaterial().getId());
            Float requiredQuantity = ingredient.getQuantity();
            if(materialOptional.isPresent()) {
                if (materialOptional.get().getQuantity() < requiredQuantity) {
                    return false;
                }
            }
        }
        materialRepository.saveAll(materials);
        return true;
    }

    public Float getMaterialAmount(List<Ingredient> ingredientListOfTheProduct) {
        List<Material> materials = new ArrayList<>();
        Float totalAmount = 0f;
        for (Ingredient ingredient : ingredientListOfTheProduct) {
            Optional<Material> materialOptional = materialRepository.findById(ingredient.getMaterial().getId());
            Float requiredQuantity = ingredient.getQuantity();
            if(materialOptional.isPresent()) {
                Material material = materialOptional.get();
                Float quantity = material.getQuantity();
                Float amount = material.getAmount();
                Float amountForOnePoint = amount / quantity;

                totalAmount += (amountForOnePoint * ingredient.getQuantity());
                    Float materialQuantity = material.getQuantity() - requiredQuantity;
                    Float materialAmount = material.getAmount() - (ingredient.getQuantity() * amountForOnePoint);
                    material.setQuantity(materialQuantity);
                    material.setAmount(materialAmount);
                    materials.add(materialOptional.get());
                    materialRepository.save(material);
            }
        }
        return totalAmount;
    }

    @Override
    public void increaseProductQuantity(Long productId, Float productQuantity) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            productOptional.get().setQuantity(productOptional.get().getQuantity() + productQuantity);
            productRepository.save(productOptional.get());
        }
    }

}
