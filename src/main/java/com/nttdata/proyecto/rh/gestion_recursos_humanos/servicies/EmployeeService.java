package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies;

import java.util.List;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Employee;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.EmployeeDto;

public interface EmployeeService {
    
    public Employee registerEmployee(EmployeeDto request);

    public List<Employee> getEmployees();

    public Employee updateEmployee(Long id, Employee newEmployee);

    public void deleteEmployee(Long id);

    public Employee getEmployee(Long id);
    
    public void updateDepartmentPos(Long id, Long newDepartmentId, String newPosition);

    public void updateStatus(Long id, String newStatus);

    public double getNetSalary(Long id);

    public int calculateAvailableVacationDays(Long employeeId);
}

