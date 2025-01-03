package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Employee;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@RequestBody Employee employee) {
        try {
            Employee registeredEmployee = employeeService.registerEmployee(employee);
            return new ResponseEntity<>(registeredEmployee, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getEmployees() {
        try {
            List<Employee> registeredEmployees = employeeService.getEmployees();
            return new ResponseEntity<>(registeredEmployees, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok("Empleado eliminado con éxito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("Empleado no encontrado con id: " + id);
        }
    }
}
