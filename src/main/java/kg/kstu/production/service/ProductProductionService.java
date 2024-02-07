package kg.kstu.production.service;

import kg.kstu.production.entity.Ingredient;
import kg.kstu.production.entity.ProductProduction;

import java.util.List;

public interface ProductProductionService {
    List<ProductProduction> getAll();

    String create(ProductProduction productProduction);

    List<Ingredient> getIngredientsOfProduct(Long productId, Float productQuantity);

    Boolean checkMaterialQuantity(List<Ingredient> ingredientListOfTheProduct);

    void increaseProductQuantity(Long productId, Float productQuantity);
}
