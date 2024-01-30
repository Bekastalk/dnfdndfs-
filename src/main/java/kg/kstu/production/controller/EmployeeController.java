package kg.kstu.production.controller;

import kg.kstu.production.entity.Employee;
import kg.kstu.production.service.EmployeeService;
import kg.kstu.production.service.PositionService;
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
public class EmployeeController {

    final EmployeeService employeeService;
    final PositionService positionService;

    /*@RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "home";
    }*/

    @RequestMapping(value = "/employee-list", method = RequestMethod.GET)
    public String getAllEmployee(Model model) {
        model.addAttribute("employees", employeeService.getAll());
        return "employees";
    }

    @RequestMapping(value = "/employee/create", method = RequestMethod.GET)
    public String employeeCreate(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("positions", positionService.getAll());
        return "createEmployee";
    }

    @RequestMapping(value = "/employee-create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String createEmployee(Employee employee) {
        employeeService.createEmployee(employee);
        return "redirect:/employee-list";
    }

    @RequestMapping(value = "/employee-delete/{id}", method = RequestMethod.GET)
    public String deleteById(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employee-list";
    }

    @RequestMapping(value = "/employee-edit/{employeeId}", method = RequestMethod.GET)
    public String employeeEdit(Model model, @PathVariable("employeeId") Long employeeId) {
        Optional<Employee> employeeOptional = employeeService.findById(employeeId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            model.addAttribute("employee", employee);
            model.addAttribute("positions", positionService.getAll());
            return "editEmployee";
        } else {
            // Обработка случая, когда сотрудник с заданным ID не найден
            return "redirect:/employee-list";
        }
    }

    @RequestMapping(value = "/employee-edit/{employeeId}", method = RequestMethod.POST)
    public String editById(@PathVariable("employeeId") Long employeeId, @ModelAttribute("employee") Employee employee) {
        employee.setId(employeeId);
        employeeService.updateEmployee(employee);
        return "redirect:/employee-list";
    }
}