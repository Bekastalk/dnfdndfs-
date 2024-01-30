package kg.kstu.production.controller;

import kg.kstu.production.entity.Ingredient;
import kg.kstu.production.entity.Product;
import kg.kstu.production.service.IngredientsService;
import kg.kstu.production.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/ingredients")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Controller
public class IngredientController {

    final ProductService productService;
    final IngredientsService ingredientsService;

    @GetMapping("/list/{productId}")
    public String getIngredientsByProduct(@PathVariable Long productId, Model model) {
        Optional<Product> productOptional = productService.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("product", product);
            model.addAttribute("ingredients", ingredientsService.getAll(productId));
            return "ingredients";
        } else {
            // Обработка случая, когда продукт не найден
            return "redirect:/products"; // Например, перенаправление на страницу со списком продуктов
        }
    }

    @GetMapping("/create/{productId}")
    public String showCreateIngredientForm(@PathVariable Long productId, Model model) {
        model.addAttribute("ingredient", new Ingredient());
        model.addAttribute("productId", productId);
        // Добавьте другие атрибуты по необходимости
        return "createIngredient";
    }

    /*
    @GetMapping("/create/{productId}")
    public String showCreateIngredientForm(@PathVariable Long productId, Model model) {
        model.addAttribute("ingredient", new Ingredient());
        model.addAttribute("productId", productId);
        // Добавьте другие атрибуты по необходимости
        return "createIngredient";
    }

    @PostMapping("/create/{productId}")
    public String createIngredient(@PathVariable Long productId, @ModelAttribute("ingredient") Ingredient ingredient) {
        // Установите связь с продуктом
        ingredient.setProduct(new Product(productId));
        ingredientsService.createIngredient(ingredient);
        return "redirect:/ingredients/list/" + productId;
    }

    @GetMapping("/delete/{id}")
    public String deleteIngredientById(@PathVariable Long id) {
        // Необходимо сохранять информацию о продукте, к которому относится ингредиент
        Long productId = ingredientsService.findById(id)
                .map(ingredient -> ingredient.getProduct().getId())
                .orElse(null);

        ingredientsService.deleteIngredient(id);

        if (productId != null) {
            return "redirect:/ingredients/list/" + productId;
        } else {
            return "redirect:/ingredients/list"; // В случае ошибки
        }
    }

    @GetMapping("/edit/{ingredientId}")
    public String showEditIngredientForm(Model model, @PathVariable("ingredientId") Long ingredientId) {
        Optional<Ingredient> ingredientOptional = ingredientsService.findById(ingredientId);
        if (ingredientOptional.isPresent()) {
            Ingredient ingredient = ingredientOptional.get();
            model.addAttribute("ingredient", ingredient);
            // Добавьте другие атрибуты по необходимости
            return "editIngredient";
        } else {
            // Обработка случая, когда ингредиент с заданным ID не найден
            return "redirect:/ingredients/list";
        }
    }

    @PostMapping("/edit/{ingredientId}")
    public String editIngredientById(@PathVariable("ingredientId") Long ingredientId,
                                     @ModelAttribute("ingredient") Ingredient ingredient) {
        // Необходимо сохранять информацию о продукте, к которому относится ингредиент
        Long productId = ingredientsService.findById(ingredientId)
                .map(ingr -> ingr.getProduct().getId())
                .orElse(null);

        ingredient.setId(ingredientId);
        ingredientsService.updateIngredient(ingredient);

        if (productId != null) {
            return "redirect:/ingredients/list/" + productId;
        } else {
            return "redirect:/ingredients/list"; // В случае ошибки
        }
    }*/
}

