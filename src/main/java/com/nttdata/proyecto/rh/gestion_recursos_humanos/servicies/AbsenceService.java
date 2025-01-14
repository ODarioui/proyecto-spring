package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies;

import java.util.List;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Absence;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.AbsenceRequestDto;

public interface AbsenceService {

    public Absence registerAbsence(AbsenceRequestDto newAbsence);
    
    public void updateStatus(Long absenceId, String newStatus);

    public List<Absence> getAbsenceHistory(Long employeeId);

    public int calculateDaysAbsence(Long employeeId);

    public List<Absence> getVacationHistory(Long employeeId);
    
}
