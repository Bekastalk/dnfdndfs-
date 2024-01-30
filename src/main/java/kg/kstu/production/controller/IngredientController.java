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
import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println("\n\n\n\n\n\nИнгредиент:\n\n\n\n\n\n\n");
        System.out.println("ING: " + ingredient.getMaterial());
        // Проверки и сохранение ингредиента
        ingredientsService.createIngredient(ingredient);

        // Перенаправить на страницу со списком ингредиентов для выбранного продукта
        return "redirect:/ingredients/list/" + ingredient.getProduct().getId();
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

