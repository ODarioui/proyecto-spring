package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Absence;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.AbsenceRequest;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.AbsenceService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/absences")
public class AbsenceController {

    @Autowired
    private AbsenceService absenceService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAbsence(@RequestBody AbsenceRequest newAbsence) {
        try {
            Absence registeredAbsence = absenceService.registerAbsence(newAbsence);
            return new ResponseEntity<>(registeredAbsence, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/approve-absence/{absenceId}")
    public ResponseEntity<String> approveAbsence(@PathVariable Long absenceId) {
        try {
            absenceService.updateStatus(absenceId,"Aprobada");
            return ResponseEntity.ok("Ausencia aceptada");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        
    }

    @PostMapping("/reject-absence/{absenceId}")
    public ResponseEntity<String> rejectAbsence(@PathVariable Long absenceId) {
        try {
            absenceService.updateStatus(absenceId,"Rechazada");
            return ResponseEntity.ok("Ausencia rechazada");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        
    }

    @GetMapping("/get-history/{id}")
    public ResponseEntity<?> getAbsenceHistory(@PathVariable Long id) {
        try {
            List<Absence> history = absenceService.getAbsenceHistory(id);
            return ResponseEntity.ok(history);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    
}
