package kg.kstu.production.controller;

import kg.kstu.production.entity.Ingredient;
import kg.kstu.production.entity.Material;
import kg.kstu.production.entity.Product;
import kg.kstu.production.service.IngredientsService;
import kg.kstu.production.service.MaterialService;
import kg.kstu.production.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/ingredients")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Controller
public class IngredientController {

    final ProductService productService;
    final IngredientsService ingredientsService;
    final MaterialService materialService;

    @GetMapping("/list/{productId}")
    public String getIngredientsByProduct(@PathVariable Long productId, Model model) {
        Optional<Product> productOptional = productService.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("product", product);
            model.addAttribute("ingredients", ingredientsService.getAll(productId));
            model.addAttribute("materials", materialService.getAll());
            return "ingredients";
        } else {
            // Обработка случая, когда продукт не найден
            return "redirect:/products"; // Например, перенаправление на страницу со списком продуктов
        }
    }

    // Метод для отображения формы создания ингредиента для конкретного продукта
    @GetMapping("/create/{productId}")
    public String showCreateIngredientForm(@PathVariable Long productId, Model model) {
        Optional<Product> productOptional = productService.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("product", product);

            List<Material> materials = materialService.getAll(); // Получите список материалов
            model.addAttribute("materials", materials);

            Ingredient ingredient = new Ingredient();
            model.addAttribute("ingredient", ingredient);

            return "createIngredient";
        } else {
            return "redirect:/products";
        }
    }

    // Метод для обработки создания ингредиента
    @PostMapping("/create")
    public String createIngredient(@ModelAttribute Ingredient ingredient) {
        ingredientsService.createIngredient(ingredient);
        return "redirect:/ingredients/list/" + ingredient.getProduct().getId();
    }

    // Метод для отображения формы создания ингредиента для конкретного продукта
    @GetMapping("/update/{ingredientId}")
    public String showUpdateIngredientForm(@PathVariable Long ingredientId, Model model) {
        Optional<Ingredient> ingredientOptional = ingredientsService.findById(ingredientId);

        if (ingredientOptional.isPresent()) {
            Ingredient ingredient = ingredientOptional.get();
            model.addAttribute("ingredient", ingredient);

            Product product = ingredient.getProduct();
            model.addAttribute("product", product);

            List<Material> materials = materialService.getAll(); // Получите список материалов
            model.addAttribute("materials", materials);

            return "editIngredient";
        } else {
            return "redirect:/products";
        }
    }

    @PostMapping("/update")
    public String updateIngredient(@ModelAttribute Ingredient ingredient) {
        // Проверки и сохранение ингредиента
        ingredientsService.updateIngredient(ingredient);

        // Перенаправить на страницу со списком ингредиентов для выбранного продукта
        return "redirect:/ingredients/list/" + ingredient.getProduct().getId();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteById(@PathVariable Long id) {
        Optional<Ingredient> ingredientOptional = ingredientsService.findById(id);
        if (ingredientOptional.isPresent()) {
            ingredientsService.deleteIngredient(id);
            return "redirect:/ingredients/list/" + ingredientOptional.get().getProduct().getId();
        } else {
            return "redirect:/products";
        }
    }
}

