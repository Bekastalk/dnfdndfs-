package kg.kstu.production.controller;

import kg.kstu.production.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("salary")
@RequiredArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;

    @GetMapping("createSalaryList")
    public String createList () {
        return "redirect:/list/{year}/{month}";
    }


    @GetMapping("/list/{year}/{month}")
    public String getAll(Model model, @PathVariable Integer year, @PathVariable Integer month) {
        model.addAttribute("salaries", salaryService.getAll(year, month));
        return "salariesOfYearAndMonth";
    }
}
