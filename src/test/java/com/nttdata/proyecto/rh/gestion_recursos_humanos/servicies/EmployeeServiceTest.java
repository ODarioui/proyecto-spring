package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Absence;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Department;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.DepartmentHead;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Employee;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.User;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.EmployeeDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.AbsenceRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.DepartmentHeadRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.DepartmentRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.EmployeeRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.UserRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentHeadRepository departmentHeadRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AbsenceRepository absenceRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void registerEmployeeTest() {
        EmployeeDto request = new EmployeeDto();

        request.setUserId(1L);
        request.setDepartmentId(10L);
        request.setBirthDate(new Date(0));
        request.setBonuses(100);
        request.setDeductions(50);
        request.setHireDate(new Date(0));
        request.setPosition("Junior");
        request.setSalary(1500);
        request.setStatus("active");

        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(employeeRepository.save(any(Employee.class))).thenReturn(new Employee());

        Employee result = employeeService.registerEmployee(request);

        assertNotNull(result);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void getEmployeesTest() {
        List<Employee> employees = List.of(new Employee(), new Employee());

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getEmployees();

        assertEquals(2, result.size());

        // Comprobacion de .filter y lista vacía
        when(employeeRepository.findAll()).thenReturn(List.of());
        assertThrows(IllegalArgumentException.class, () -> employeeService.getEmployees());
    }

    @Test
    void updateEmployeeTest() {
        Employee employee = new Employee();
        employee.setBonuses(240.0);

        when(employeeRepository.findById(2L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee updatedEmployee = employeeService.updateEmployee(2L, employee);

        assertEquals(240.0, updatedEmployee.getBonuses());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void deleteEmployeeTest() {
        Long id = 1L;
        Employee employee = new Employee();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        // Asignar department head al empleado para comprobar el forEach del
        // deleteEmployee
        DepartmentHead departmentHead = new DepartmentHead();
        when(departmentHeadRepository.findByEmployee(employee)).thenReturn(List.of(departmentHead));
        when(absenceRepository.findByEmployee(employee)).thenReturn(List.of());

        employeeService.deleteEmployee(id);

        verify(employeeRepository, times(1)).deleteById(id);
        verify(departmentHeadRepository, times(1)).findByEmployee(employee);
        verify(absenceRepository, times(1)).deleteAll(List.of());

        // Verificar que testea bien la excepcion cuando no encuentra al empleado
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> employeeService.deleteEmployee(id));

    }

    @Test
    void getEmployeeTest() {
        Long id = 2L;
        Employee employee = new Employee();

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        Employee foundEmployee = employeeService.getEmployee(id);

        assertNotNull(foundEmployee);
        assertEquals(employee, foundEmployee);

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> employeeService.getEmployee(id));
    }

    @Test
    void getNetSalaryTest() {
        Long id = 1L;
        Employee employee = new Employee();

        employee.setId(id);
        employee.setSalary(1500.0);
        employee.setDeductions(100.0);
        employee.setBonuses(200.0);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        double netSalary = employeeService.getNetSalary(id);

        assertEquals(1600.0, netSalary);
    }

    @Test
    void updateDepartmentPosTest() {
        Long id = 2L;
        Long newDepartmentId = 3L;
        String newPosition = "Junior";
        Employee employee = new Employee();
        Department department = new Department();

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        when(departmentRepository.findById(newDepartmentId)).thenReturn(Optional.of(department));

        employeeService.updateDepartmentPos(id, newDepartmentId, newPosition);

        assertEquals(newPosition, employee.getPosition());
        assertEquals(newDepartmentId, employee.getDepartmentId());

    }

    @Test
    void updateStatusTest() {
        Long id = 2L;
        Employee employee = new Employee();
        String newStatus = "INACTIVE";

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        employeeService.updateStatus(id, newStatus);

        assertEquals(newStatus, employee.getStatus());

    }

    @Test
    void calculateAvailableVacationDaysTest() {
        Long id = 2L;
        Employee employee = new Employee();
        employee.setAvailableVacationDays(20);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        Absence absence1 = new Absence();
        absence1.setAbsenceType("Vacaciones");
        absence1.setStatus("Aprobada");
        absence1.setStartDate(LocalDate.of(2023, 3, 15));
        absence1.setEndDate(LocalDate.of(2023, 3, 20));

        Absence absence2 = new Absence();
        absence2.setAbsenceType("Vacaciones");
        absence2.setStatus("Aprobada");
        absence2.setStartDate(LocalDate.of(2023, 3, 22));
        absence2.setEndDate(LocalDate.of(2023, 3, 24));

        when(absenceRepository.findByEmployee(employee)).thenReturn(List.of(absence1, absence2));

        assertEquals(11, employeeService.calculateAvailableVacationDays(id));

    }

    @Test
    void calculateAvailableVacationDaysTest_NoVacationAbsence() {
        Long id = 2L;
        Employee employee = new Employee();
        employee.setAvailableVacationDays(20);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        Absence absence1 = new Absence();
        absence1.setAbsenceType("Vacaciones");
        absence1.setStatus("Aprobada");
        absence1.setStartDate(LocalDate.of(2023, 3, 15));
        absence1.setEndDate(LocalDate.of(2023, 3, 20));

        Absence absence2 = new Absence();
        absence2.setAbsenceType("Enfermedad");
        absence2.setStatus("Aprobada");
        absence2.setStartDate(LocalDate.of(2023, 3, 22));
        absence2.setEndDate(LocalDate.of(2023, 3, 24));

        when(absenceRepository.findByEmployee(employee)).thenReturn(List.of(absence1, absence2));

        assertEquals(14, employeeService.calculateAvailableVacationDays(id));
    }

}
