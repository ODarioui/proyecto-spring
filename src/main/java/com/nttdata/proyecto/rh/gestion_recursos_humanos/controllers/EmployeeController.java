package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Employee;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.EmployeeDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.AbsenceService;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.EmployeeService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AbsenceService absenceService;

    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@RequestBody EmployeeDto request) {
        try {
            Employee registeredEmployee = employeeService.registerEmployee(request);
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

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id) {
        try {
            Employee registeredEmployee = employeeService.getEmployee(id);
            return new ResponseEntity<>(registeredEmployee, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/update-dep-pos/{id}")
    public ResponseEntity<String> updateDepartmentPos(@PathVariable Long id, @RequestParam Long newDepartmentId, @RequestParam String newPosition) {
        try {
            employeeService.updateDepartmentPos(id, newDepartmentId, newPosition);
            return ResponseEntity.ok("Departamento y posiciones nuevas actualizadas con éxito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam String newStatus) {
        try {
            employeeService.updateStatus(id, newStatus);
            return ResponseEntity.ok("Estado actualizado con éxito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/get-salary/{id}")
    public ResponseEntity<?> getSalary(@PathVariable Long id) {
        try {
            double netSalary = employeeService.getNetSalary(id);
            return ResponseEntity.ok(netSalary);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/calculate-absences/{employeeId}")
    public ResponseEntity<String> calculateAbsenceDays(@PathVariable Long employeeId) {
        try {
            int totalDays = absenceService.calculateDaysAbsence(employeeId);
            return ResponseEntity.ok("Total absence days: " + totalDays);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/vacation-balance/{employeeId}")
    public ResponseEntity<String> getVacationBalance(@PathVariable Long employeeId) {
        try {
            int availableDays = employeeService.calculateAvailableVacationDays(employeeId);
            return ResponseEntity.ok("El saldo de días de vacaciones disponibles es: " + availableDays);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
