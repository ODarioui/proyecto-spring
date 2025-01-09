package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Absence;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.AbsenceRequest;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.AbsenceService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/absence")
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
    public ResponseEntity<?> approveAbsence(@PathVariable Long absenceId) {
        try {
            Absence absenceToUpdate = absenceService.updateStatus(absenceId,"Aprobada");
            return new ResponseEntity<>(absenceToUpdate, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        
    }

    @PostMapping("/reject-absence/{absenceId}")
    public ResponseEntity<?> rejectAbsence(@PathVariable Long absenceId) {
        try {
            Absence absenceToUpdate = absenceService.updateStatus(absenceId,"Rechazada");
            return new ResponseEntity<>(absenceToUpdate, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        
    }
    
    
}
