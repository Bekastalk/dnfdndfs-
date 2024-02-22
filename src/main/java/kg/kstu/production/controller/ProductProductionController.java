package kg.kstu.production.controller;

import kg.kstu.production.entity.ProductProduction;
import kg.kstu.production.service.BudgetService;
import kg.kstu.production.service.EmployeeService;
import kg.kstu.production.service.ProductProductionService;
import kg.kstu.production.service.ProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product/production")
public class ProductProductionController {
    private final ProductProductionService productionService;
    private final ProductService productService;
    private final EmployeeService employeeService;
    private final BudgetService budgetService;

    @GetMapping(value = "/list")
    public String getAll(Model model) {
        model.addAttribute("productions", productionService.getAll());
        model.addAttribute("budgets", budgetService.getBudget());
        return "productions";
    }

    @GetMapping(value = "/create")
    public String create(Model model) {
        model.addAttribute("products", productService.getAll());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("thisTime", LocalDateTime.now());
        model.addAttribute("productProduction", new ProductProduction());
        return "createProduction";
    }

    @PostMapping(value = "/save")
    public String save(ProductProduction productProduction, Model model) {
        String result = productionService.create(productProduction);
        if(result.equals("Done")) {
            return "redirect:/product/production/list";
        } else {
            model.addAttribute("errorMessage", result);
            model.addAttribute("products", productService.getAll());
            model.addAttribute("employees", employeeService.getAll());
            model.addAttribute("thisTime", LocalDateTime.now());
            return "createProduction";
        }
    }
}
