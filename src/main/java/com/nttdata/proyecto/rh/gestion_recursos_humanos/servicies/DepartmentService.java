package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies;

import java.util.Map;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Department;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.DepartmentHead;

public interface DepartmentService {

    public Department createDepartment(Department department);

    public Department updateDepartment(Department department);

    public void deleteDepartment(Department department);

    public DepartmentHead addHeadToDepartment(DepartmentHead departmentHead);

    public Map<String, Object> getEmployeesByDepartment(Long department_id);
}
