package kg.kstu.production.service;


import kg.kstu.production.entity.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientsService {

    List<Ingredient> getAll(Long productId);

    String createIngredient(Ingredient ingredient);

    void deleteIngredient(Long id);

    Optional<Ingredient> findById(Long id);

    String updateIngredient(Ingredient ingredient);
}
