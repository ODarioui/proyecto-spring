package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.DepartmentHead;

public interface DepartmentHeadService {

    public DepartmentHead registerDepartmentHead(Long departmentId, Long employeeId);

    public void setDepartmentHead(Long id, Long departmentId, Long employeeId);
    
}
