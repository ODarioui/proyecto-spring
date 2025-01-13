package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.EmployeeService;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.AbsenceRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.DepartmentHeadRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.DepartmentRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.EmployeeRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.UserRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Absence;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Department;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.DepartmentHead;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Employee;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.EmployeeRequest;

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
    
    @Transactional
    public Employee registerEmployee(EmployeeRequest request){
        
        if (request.getUserId() == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        if(request.getDepartmentId() == null){
            throw new IllegalArgumentException("Departamento no encontrado");
        }

        Employee employee = new Employee();

        employee.setUser(userRepository.findById(request.getUserId()).get());
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
        List<Employee> listEmployees = employeeRepository.findAll();
        
        if(listEmployees.isEmpty())
            throw new IllegalArgumentException("No hay empleados registrados");

        return listEmployees;
    }

    @Transactional
    public Employee updateEmployee(Long id, Employee newEmployee){
        Optional<Employee> oldEmployee = employeeRepository.findById(id);

        if (!oldEmployee.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + id);

        oldEmployee.get().setBirthDate(newEmployee.getBirthDate());
        oldEmployee.get().setHireDate(newEmployee.getHireDate());
        oldEmployee.get().setPosition(newEmployee.getPosition());
        oldEmployee.get().setSalary(newEmployee.getSalary());
        oldEmployee.get().setStatus(newEmployee.getStatus());
        oldEmployee.get().setDepartmentId(newEmployee.getDepartmentId());

        return employeeRepository.save(oldEmployee.get());
    }

    @Transactional
    public void deleteEmployee(Long id){
        Optional<Employee> oldEmployee = employeeRepository.findById(id);

        if (!oldEmployee.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + id);
        
        List<DepartmentHead> departments = departmentHeadRepository.findByEmployee(oldEmployee.get());
        for (DepartmentHead department : departments) {
            department.setEmployee(null);
            departmentHeadRepository.save(department);
        }

        List<Absence> absences = absenceRepository.findByEmployee(oldEmployee.get());
        absenceRepository.deleteAll(absences);

        employeeRepository.deleteById(id);
    }

    @Transactional
    public Employee getEmployee(Long id){
        Optional<Employee> foundEmployee = employeeRepository.findById(id);
        
        if(!foundEmployee.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + id);

        return foundEmployee.get();
    }

    @Transactional
    public void updateDepartmentPos(Long id, Long newDepartmentId, String newPosition){
        Optional<Employee> foundEmployee = employeeRepository.findById(id);
        Optional<Department> newDepartment = departmentRepository.findById(newDepartmentId);

        if (!foundEmployee.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + id);
        if (!newDepartment.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + id);

        foundEmployee.get().setPosition(newPosition);
        foundEmployee.get().setDepartmentId(newDepartmentId);

    }

    @Transactional
    public void updateStatus(Long id, String newStatus){
        Optional<Employee> foundEmployee = employeeRepository.findById(id);

        if (!foundEmployee.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + id);

        foundEmployee.get().setStatus(newStatus);
    }

    @Transactional
    public double getNetSalary(Long id){
        Optional<Employee> foundEmployee = employeeRepository.findById(id);

        if (!foundEmployee.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + id);

        double netSalary = foundEmployee.get().getSalary() + foundEmployee.get().getBonuses()
                - foundEmployee.get().getDeductions();

        return netSalary;
    }

}
