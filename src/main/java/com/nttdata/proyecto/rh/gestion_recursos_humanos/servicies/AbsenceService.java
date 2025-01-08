package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies;

import java.util.List;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Absence;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.AbsenceRequest;

public interface AbsenceService {

    public Absence registerAbsence(AbsenceRequest newAbsence);
    
    public Absence updateStatus(Long absenceId, String newStatus);

    public List<Absence> getAbsenceHistory(Long userId);
    
}
