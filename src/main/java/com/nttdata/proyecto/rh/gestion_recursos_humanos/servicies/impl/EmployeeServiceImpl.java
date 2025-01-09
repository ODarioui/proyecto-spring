package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.EmployeeService;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.DepartmentRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.EmployeeRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Department;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository EmployeeRepository;

    @Autowired
    private DepartmentRepository DepartmentRepository;

    @Transactional
    public Employee registerEmployee(Employee employee) {

        if (employee.getId() != null && EmployeeRepository.existsById(employee.getId())) {
            throw new IllegalArgumentException("Error.Empleado ya registrado en la base de datos");
        }
        Employee employeeNew = EmployeeRepository.save(employee);

        return employeeNew;
    }

    @Transactional
    public List<Employee> getEmployees() {
        List<Employee> listEmployees = EmployeeRepository.findAll();

        if (listEmployees.isEmpty())
            throw new IllegalArgumentException("No hay empleados registrados");

        return listEmployees;
    }

    @Transactional
    public Employee updateEmployee(Long id, Employee newEmployee) {
        Optional<Employee> oldEmployee = EmployeeRepository.findById(id);

        if (!oldEmployee.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + id);

        oldEmployee.get().setBirthDate(newEmployee.getBirthDate());
        oldEmployee.get().setHireDate(newEmployee.getHireDate());
        oldEmployee.get().setPosition(newEmployee.getPosition());
        oldEmployee.get().setSalary(newEmployee.getSalary());
        oldEmployee.get().setStatus(newEmployee.getStatus());
        oldEmployee.get().setDepartmentId(newEmployee.getDepartmentId());

        return EmployeeRepository.save(oldEmployee.get());
    }

    @Transactional
    public void deleteEmployee(Long id) {
        Optional<Employee> oldEmployee = EmployeeRepository.findById(id);

        if (!oldEmployee.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + id);

        EmployeeRepository.deleteById(id);
    }

    @Transactional
    public Employee getEmployee(Long id) {
        Optional<Employee> foundEmployee = EmployeeRepository.findById(id);

        if (!foundEmployee.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + id);

        return foundEmployee.get();
    }

    @Transactional
    public void updateDepartmentPos(Long id, Long newDepartmentId, String newPosition) {
        Optional<Employee> foundEmployee = EmployeeRepository.findById(id);
        Optional<Department> newDepartment = DepartmentRepository.findById(newDepartmentId);

        if (!foundEmployee.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + id);
        if (!newDepartment.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + id);

        foundEmployee.get().setPosition(newPosition);
        foundEmployee.get().setDepartmentId(newDepartmentId);

    }

    @Transactional
    public void updateStatus(Long id, String newStatus) {
        Optional<Employee> foundEmployee = EmployeeRepository.findById(id);

        if (!foundEmployee.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + id);

        foundEmployee.get().setStatus(newStatus);
    }

    @Transactional
    public double getNetSalary(Long id) {
        Optional<Employee> foundEmployee = EmployeeRepository.findById(id);

        if (!foundEmployee.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + id);

        double netSalary = foundEmployee.get().getSalary() + foundEmployee.get().getBonuses()
                - foundEmployee.get().getDeductions();

        return netSalary;
    }

}
