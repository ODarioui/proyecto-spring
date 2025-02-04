package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Employee;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.EmployeeDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.EmployeeService;

@Controller
@RequestMapping("/api/v1/employees")
public class EmployeeViewController {

    private final EmployeeService employeeService;

    EmployeeViewController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Secured({ "ROLE_HR", "ROLE_ADMIN" })
    @GetMapping("/list")
    public String listEmployees(Model model) {
        List<Employee> employees = employeeService.getEmployees();
        model.addAttribute("employees", employees);
        return "employees-list";
    }

    @GetMapping("/new")
    public String newEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        return "employees-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute EmployeeDto employee) {
        employeeService.registerEmployee(employee);
        return "redirect:/api/v1/employees";
    }

}
