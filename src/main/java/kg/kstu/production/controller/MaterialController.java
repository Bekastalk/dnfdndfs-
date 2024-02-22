package kg.kstu.production.controller;

import kg.kstu.production.entity.Material;
import kg.kstu.production.entity.MaterialPurchase;
import kg.kstu.production.entity.Product;
import kg.kstu.production.repository.BudgetRepository;
import kg.kstu.production.service.EmployeeService;
import kg.kstu.production.service.MaterialPurchaseService;
import kg.kstu.production.service.MaterialService;
import kg.kstu.production.service.UnitOfMeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequestMapping("/material")
@RequiredArgsConstructor
public class MaterialController {
    private final MaterialService materialService;
    private final MaterialPurchaseService materialPurchaseService;
    private final EmployeeService employeeService;
    private final BudgetRepository budgetRepository;
    private final UnitOfMeasurementService unitOfMeasurementService;

    @GetMapping(value = "/list")
    public String getAllEmployee(Model model) {
        model.addAttribute("materials", materialService.getAll());
        model.addAttribute("budgets", budgetRepository.findAll());
        return "materials";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String productCreate(Model model) {
        model.addAttribute("material", new Material());
        model.addAttribute("unitOfMeasurements", unitOfMeasurementService.getAll());
        return "createMaterial";
    }

    @RequestMapping(value = "/material-create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String createProducts(Material material) {
        materialService.createMaterial(material);
        return "redirect:/material/list";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteById(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return "redirect:/material/list";
    }

    @RequestMapping(value = "/edit/{materialId}", method = RequestMethod.GET)
    public String productEdit(Model model, @PathVariable("materialId") Long materialId) {
        Optional<Material> materialOptional = materialService.findById(materialId);
        if (materialOptional.isPresent()) {
            Material material = materialOptional.get();
            model.addAttribute("material", material);
            model.addAttribute("unitOfMeasurements", unitOfMeasurementService.getAll());
            return "editMaterial";
        } else {
            return "redirect:/material/list";
        }
    }

    @RequestMapping(value = "/material-edit/{materialId}", method = RequestMethod.POST)
    public String editById(@PathVariable("materialId") Long materialId, @ModelAttribute("material") Material material) {
        material.setId(materialId);
        materialService.updateMaterial(material);
        return "redirect:/material/list";
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
