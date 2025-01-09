package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.DepartmentHead;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.DepartmentHeadRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.DepartmentRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.EmployeeRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.DepartmentHeadService;

@RestController
@RequestMapping("/api/v1/department-head")
public class DepartmentHeadController {

    @Autowired
    private DepartmentHeadService departmentHeadService;

    @Autowired
    private DepartmentHeadRepository departmentHeadRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping("/register/{departmentId}")
    public ResponseEntity<DepartmentHead> registerDepartmentHead(@PathVariable Long departmentId,
            @RequestParam Long employeeId) {
        try {
            DepartmentHead departmentHead = departmentHeadService.registerDepartmentHead(departmentId, employeeId);
            return ResponseEntity.ok(departmentHead);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/get-department-head/{departmentId}")
    public ResponseEntity<DepartmentHead> getDepartmentHead(@PathVariable Long departmentId) {
        try {
            Optional<DepartmentHead> departmentHead = departmentHeadRepository.findByDepartmentId(departmentId);
            return ResponseEntity.ok(departmentHead.get());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }

    }

    @PostMapping("/set-department-head/{id}")
    public ResponseEntity<?> setDepartmentHead(@PathVariable Long id, @RequestParam Long employeeId,
            @RequestParam Long departmentId) {
        try {
            departmentHeadService.setDepartmentHead(id, departmentId, employeeId);
            return ResponseEntity.ok("Departamento y posiciones nuevas actualizadas con éxito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }

    }

}
