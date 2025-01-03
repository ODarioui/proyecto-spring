package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.EmployeeService;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.EmployeeRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository EmployeeRepository;
    
    @Transactional
    public Employee registerEmployee(Employee employee){
        Employee employeeNew = EmployeeRepository.save(employee);

        if(EmployeeRepository.existsById(employee.getId())){
            throw new IllegalArgumentException("Error.Empleado ya registrado en la base de datos");
        }

        return employeeNew;
    }

    @Transactional
    public List<Employee> getEmployees(){
        List<Employee> listEmployees = EmployeeRepository.findAll();
        
        if(listEmployees.isEmpty())
            throw new IllegalArgumentException("No hay empleados registrados");

        return listEmployees;
    }

    @Transactional
    public Employee updateEmployee(Long id, Employee newEmployee){
        Optional<Employee> oldEmployee = EmployeeRepository.findById(id);

        if(!oldEmployee.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + id);
        
        oldEmployee.get().setBirthDate(newEmployee.getBirthDate());
        oldEmployee.get().setDepartment(newEmployee.getDepartment());
        oldEmployee.get().setHireDate(newEmployee.getHireDate());
        oldEmployee.get().setPosition(newEmployee.getPosition());
        oldEmployee.get().setSalary(newEmployee.getSalary());
        oldEmployee.get().setStatus(newEmployee.getStatus());

        return EmployeeRepository.save(oldEmployee.get());
    }

    @Transactional
    public void deleteEmployee(Long id){
        Optional<Employee> oldEmployee = EmployeeRepository.findById(id);

        if(!oldEmployee.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + id);

        EmployeeRepository.deleteById(id);
    }


}
