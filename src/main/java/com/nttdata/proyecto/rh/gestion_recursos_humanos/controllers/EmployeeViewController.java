package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.EmployeeService;

@Controller
public class EmployeeViewController {
    /* @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String listEmployees(Model model) {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employee-list";
    } */
}
