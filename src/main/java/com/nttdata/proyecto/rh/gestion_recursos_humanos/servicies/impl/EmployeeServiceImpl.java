package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl;

import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.EmployeeService;

import jakarta.persistence.EntityNotFoundException;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.AbsenceRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.DepartmentHeadRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.DepartmentRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.EmployeeRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.UserRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Absence;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Department;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.DepartmentHead;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Employee;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.EmployeeDto;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentHeadRepository departmentHeadRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AbsenceRepository absenceRepository;

    private String exceptionEmployeeNotFoundMessage = "Employee not found";
    
    @Transactional
    public Employee registerEmployee(EmployeeDto request){
        
        Optional.ofNullable(request.getUserId())
                .orElseThrow( () -> new IllegalArgumentException(exceptionEmployeeNotFoundMessage));

        Optional.ofNullable(request.getDepartmentId())
                .orElseThrow( () -> new IllegalArgumentException("Departamento no encontrado"));

        Employee employee = new Employee();

        employee.setUser(userRepository.findById(request.getUserId()).orElseThrow( () -> new EntityNotFoundException("Usuario no encontrado")));
        employee.setDepartmentId(request.getDepartmentId());
        employee.setPosition(request.getPosition());
        employee.setHireDate(request.getHireDate());
        employee.setSalary(request.getSalary());
        employee.setBonuses(request.getBonuses());
        employee.setDeductions(request.getDeductions());
        employee.setBirthDate(request.getBirthDate());
        employee.setStatus(request.getStatus());

        return employeeRepository.save(employee);
    }

    @Transactional
    public List<Employee> getEmployees(){

        return Optional.of(employeeRepository.findAll())
                        .filter(list -> !list.isEmpty())
                        .orElseThrow( () -> new IllegalArgumentException("No hay empleados registrados"));
    }

    @Transactional
    public Employee updateEmployee(Long id, Employee newEmployee){  
        return employeeRepository.findById(id)
            .map(oldEmployee -> {
                oldEmployee.setBirthDate(newEmployee.getBirthDate());
                oldEmployee.setHireDate(newEmployee.getHireDate());
                oldEmployee.setPosition(newEmployee.getPosition());
                oldEmployee.setSalary(newEmployee.getSalary());
                oldEmployee.setStatus(newEmployee.getStatus());
                oldEmployee.setDepartmentId(newEmployee.getDepartmentId());
                return employeeRepository.save(oldEmployee);
                }
            )
            .orElseThrow( () -> new IllegalArgumentException(exceptionEmployeeNotFoundMessage));
    }

    @Transactional
    public void deleteEmployee(Long id){

        employeeRepository.findById(id)
                          .ifPresentOrElse(employee -> {
                                        departmentHeadRepository.findByEmployee(employee).forEach(department -> {
                                            department.setEmployee(null);
                                            departmentHeadRepository.save(department);
                                        });

                                        absenceRepository.deleteAll(absenceRepository.findByEmployee(employee));
                                        employeeRepository.deleteById(id);
                                        }, () -> {
                                            throw new IllegalArgumentException(exceptionEmployeeNotFoundMessage);
                                        }
                                    );
}

    @Transactional
    public Employee getEmployee(Long id){
        return employeeRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException(exceptionEmployeeNotFoundMessage));
    }

    @Transactional
    public void updateDepartmentPos(Long id, Long newDepartmentId, String newPosition){
        Employee foundEmployee = employeeRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException(exceptionEmployeeNotFoundMessage));

        departmentRepository.findById(newDepartmentId)
                            .orElseThrow(() -> new IllegalArgumentException("No existe el departamento con el id: " + newDepartmentId));

        foundEmployee.setPosition(newPosition);
        foundEmployee.setDepartmentId(newDepartmentId);

        employeeRepository.save(foundEmployee);

    }

    @Transactional
    public void updateStatus(Long id, String newStatus){
        Employee foundEmployee = employeeRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(exceptionEmployeeNotFoundMessage));

        foundEmployee.setStatus(newStatus);

        employeeRepository.save(foundEmployee);
    }

    @Transactional
    public double getNetSalary(Long id){
        return employeeRepository.findById(id)
                                .map(employee -> employee.getSalary() + employee.getBonuses() - employee.getDeductions())
                                .orElseThrow( () -> new IllegalArgumentException(exceptionEmployeeNotFoundMessage));
    }

    @Transactional
    public int calculateAvailableVacationDays(Long employeeId) {
        Employee foundEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException(exceptionEmployeeNotFoundMessage));
    
        int consumedDays = absenceRepository.findByEmployee(foundEmployee).stream()
                .filter(absence -> "Vacaciones".equalsIgnoreCase(absence.getAbsenceType()) && "Aprobada".equalsIgnoreCase(absence.getStatus()))
                .filter(absence -> absence.getStartDate() != null && absence.getEndDate() != null)
                .mapToInt(absence -> (int) ChronoUnit.DAYS.between(absence.getStartDate(), absence.getEndDate()) + 1)
                .sum();
    
        foundEmployee.setUsedVacationDays(consumedDays);
        int availableDays = foundEmployee.getAvailableVacationDays() - consumedDays;
        employeeRepository.save(foundEmployee);
    
        return availableDays;
    }


}
