package kg.kstu.production.controller;

import kg.kstu.production.entity.MaterialPurchase;
import kg.kstu.production.repository.BudgetRepository;
import kg.kstu.production.service.EmployeeService;
import kg.kstu.production.service.MaterialPurchaseService;
import kg.kstu.production.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/material")
@RequiredArgsConstructor
public class MaterialController {
    private final MaterialService materialService;
    private final MaterialPurchaseService materialPurchaseService;
    private final EmployeeService employeeService;
    private final BudgetRepository budgetRepository;

    @GetMapping(value = "/list")
    public String getAllEmployee(Model model) {
        model.addAttribute("materials", materialService.getAll());
        model.addAttribute("budgets", budgetRepository.findAll());
        return "materials";
    }

    @GetMapping(value = "/purchase-list")
    public String getPurchaseList(Model model) {
        model.addAttribute("purchases", materialPurchaseService.getAll());
        model.addAttribute("budgets", budgetRepository.findAll());
        return "purchases";
    }

    @GetMapping(value = "/purchase")
    public String purchaseMaterial(Model model) {
        model.addAttribute("materials", materialService.getAll());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("thisTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        model.addAttribute("budgets", budgetRepository.findAll());
        model.addAttribute("materialPurchase", new MaterialPurchase());
        return "createPurchase";
    }

    @PostMapping(value = "/create-purchase")
    public String createPurchase(MaterialPurchase purchase, Model model) {
        String result = materialPurchaseService.create(purchase);
        if (result.equals("Done")) {
            return "redirect:/material/purchase-list";
        } else if (result.equals("null")) {
            model.addAttribute("errorMessage", "Недостаточно средств для закупки");
            model.addAttribute("materials", materialService.getAll());
            model.addAttribute("employees", employeeService.getAll());
            model.addAttribute("thisTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
            model.addAttribute("budgets", budgetRepository.findAll());
            model.addAttribute("materialPurchase", new MaterialPurchase());
            return "createPurchase";
        }
        return result;
    }
}
