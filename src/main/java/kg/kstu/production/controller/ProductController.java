package kg.kstu.production.controller;

import kg.kstu.production.entity.Product;
import kg.kstu.production.service.ProductService;
import kg.kstu.production.service.UnitOfMeasurementService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductController {
    final ProductService productService;
    final UnitOfMeasurementService unitOfMeasurementService;

    @RequestMapping(value = "/product-list", method = RequestMethod.GET)
    public String getAllProduct(Model model) {
        model.addAttribute("products", productService.getAll());
        return "products";
    }

    @RequestMapping(value = "/product/create", method = RequestMethod.GET)
    public String productCreate(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("unitOfMeasurements", unitOfMeasurementService.getAll());
        return "createProduct";
    }

    @RequestMapping(value = "/product-create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String createProducts(Product product) {
        productService.createProduct(product);
        return "redirect:/product-list";
    }

    @RequestMapping(value = "/product-delete/{id}", method = RequestMethod.GET)
    public String deleteById(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/product-list";
    }

    @RequestMapping(value = "/product-edit/{productId}", method = RequestMethod.GET)
    public String productEdit(Model model, @PathVariable("productId") Long productId) {
        Optional<Product> productOptional = productService.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("product", product);
            model.addAttribute("unitOfMeasurements", unitOfMeasurementService.getAll());
            return "editProduct";
        } else {
            // Обработка случая, когда продукт с заданным ID не найден
            return "redirect:/product-list";
        }
    }

    @RequestMapping(value = "/product-edit/{productId}", method = RequestMethod.POST)
    public String editById(@PathVariable("productId") Long productId, @ModelAttribute("product") Product product) {
        product.setId(productId);
        productService.updateProduct(product);
        return "redirect:/product-list";
    }
}
