package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Department;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.DepartmentHead;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Employee;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.DepartmentHeadRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.DepartmentRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.EmployeeRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.DepartmentHeadService;

@Service
public class DepartmentHeadImpl implements DepartmentHeadService {

    @Autowired
    private DepartmentHeadRepository departmentHeadRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional
    public DepartmentHead registerDepartmentHead(Long departmentId, Long employeeId){
        Employee employee = employeeRepository.findById(employeeId).get();
        Department department = departmentRepository.findById(employeeId).get();

        DepartmentHead departmentHead = new DepartmentHead(department,employee);

        return departmentHeadRepository.save(departmentHead);
    }

    @Transactional
    public void setDepartmentHead(Long id, Long departmenId, Long employeeId){
        Optional <Employee> employee = employeeRepository.findById(employeeId);
        Optional <Department> department = departmentRepository.findById(departmenId);
        Optional<DepartmentHead> departmentHead = departmentHeadRepository.findById(id);

        if(!employee.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + employeeId);
        
        if(!department.isPresent())
            throw new IllegalArgumentException("No existe el departamento con el id: " + departmenId);
        
        if(!departmentHead.isPresent())
            throw new IllegalArgumentException("No existe el director de departamento con el id: " + id);

        departmentHead.get().setDepartment(department.get());
        departmentHead.get().setEmployee(employee.get());

    }

}
