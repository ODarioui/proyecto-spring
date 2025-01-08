package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Absence;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Employee;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.AbsenceRequest;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.AbsenceRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.EmployeeRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.AbsenceService;

@Service
public class AbsenseServiceImpl implements AbsenceService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AbsenceRepository absenceRepository;

    public Absence registerAbsence (AbsenceRequest newAbsence){
        Optional<Employee> employee = employeeRepository.findById(newAbsence.getEmployeeId());

        if(!employee.isPresent())
            throw new IllegalArgumentException("No existe el departamento con el id: " + employee.get().getId());
    
        Absence absence = new Absence();
        absence.setEmployee(employee.get());
        absence.setAbsenceType(newAbsence.getAbsenceType());
        absence.setStartDate(newAbsence.getStartDate());
        absence.setEndDate(newAbsence.getEndDate());
        absence.setStatus(newAbsence.getStatus());

        return absenceRepository.save(absence);
    }

    public Absence updateStatus(Long absenceId, String newStatus){
        Optional<Absence> absence = absenceRepository.findById(absenceId);

        if(!absence.isPresent())
            throw new IllegalArgumentException("No existe la ausencia con el id: " + absence.get().getId());

        absence.get().setStatus(newStatus);

        return absenceRepository.save(absence.get());
    }

    public List<Absence> getAbsenceHistory(Long userId){
        List<Absence> lista = null;

        return lista;
    }

}
