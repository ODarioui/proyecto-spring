package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.exceptions.CustomException;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Absence;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.AbsenceRequestDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.AbsenceService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/absences")
public class AbsenceController {

    private final AbsenceService absenceService;

    public AbsenceController(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    @PostMapping("/register")
    public ResponseEntity<Absence> registerAbsence(@RequestBody AbsenceRequestDto newAbsence) {
        try {
            Absence registeredAbsence = absenceService.registerAbsence(newAbsence);
            return new ResponseEntity<>(registeredAbsence, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Secured({ "ROLE_HR", "ROLE_ADMIN" })
    @PostMapping("/approve-absence/{absenceId}")
    public ResponseEntity<String> approveAbsence(@PathVariable Long absenceId) {
        try {
            absenceService.updateStatus(absenceId, "Aprobada");
            return ResponseEntity.ok("Ausencia aceptada");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @Secured({ "ROLE_HR", "ROLE_ADMIN" })
    @PostMapping("/reject-absence/{absenceId}")
    public ResponseEntity<String> rejectAbsence(@PathVariable Long absenceId) {
        try {
            absenceService.updateStatus(absenceId, "Rechazada");
            return ResponseEntity.ok("Ausencia rechazada");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/get-history/{id}")
    public ResponseEntity<List<Absence>> getAbsenceHistory(@PathVariable Long id) {
        try {
            List<Absence> history = absenceService.getAbsenceHistory(id);
            return ResponseEntity.ok(history);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/vacation-history/{employeeId}")
    public ResponseEntity<List<Absence>> getVacationHistory(@PathVariable Long employeeId) {
        try {
            List<Absence> vacations = absenceService.getVacationHistory(employeeId);
            return ResponseEntity.ok(vacations);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
