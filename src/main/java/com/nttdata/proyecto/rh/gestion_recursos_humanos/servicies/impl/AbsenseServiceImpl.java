package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Absence;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Employee;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.AbsenceRequestDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.AbsenceRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.EmployeeRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.AbsenceService;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.NotificationService;

@Service
public class AbsenseServiceImpl implements AbsenceService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private NotificationService notificationService;

    public Absence registerAbsence (AbsenceRequestDto newAbsence){
        Optional<Employee> employee = employeeRepository.findById(newAbsence.getEmployeeId());

        if(!employee.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + employee.get().getId());
    
        if(newAbsence.getStartDate().isAfter(newAbsence.getEndDate()))
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin.");

        Absence absence = new Absence();
        absence.setEmployee(employee.get());
        absence.setAbsenceType(newAbsence.getAbsenceType());
        absence.setStartDate(newAbsence.getStartDate());
        absence.setEndDate(newAbsence.getEndDate());
        absence.setStatus("Pendiente");

        return absenceRepository.save(absence);
    }

    public void updateStatus(Long absenceId, String newStatus){
        Optional<Absence> absence = absenceRepository.findById(absenceId);

        if(!absence.isPresent())
            throw new IllegalArgumentException("No existe la ausencia con el id: " + absence.get().getId());

        absence.get().setStatus(newStatus);

        absenceRepository.save(absence.get());

        Employee employee = absence.get().getEmployee();

        int totalDays = calculateDaysAbsence(employee.getId());
        if (totalDays > 30) {
            notificationService.sendNotification(
                    employee.getUser().getEmail(),
                    "Alerta de ausencias",
                    "Has superado el límite permitido de " + 30 + " días de ausencia. Total: " + totalDays + " días." 
            );
            newStatus = "Rechazada";
        }

        if ("Aprobada".equalsIgnoreCase(newStatus)) {
            employee.setTotalAbsenceDays(totalDays);
            notificationService.printNotification(
                    employee.getUser().getEmail(),
                    "Ausencia aprobada",
                    "Tu ausencia del " + absence.get().getStartDate() + " al " + absence.get().getEndDate() + " ha sido aprobada. Total días de ausencia: " + totalDays
            );
        } else if ("Rechazada".equalsIgnoreCase(newStatus)) {
            notificationService.printNotification(
                    employee.getUser().getEmail(),
                    "Ausencia rechazada",
                    "Tu ausencia del " + absence.get().getStartDate() + " al " + absence.get().getEndDate() + " ha sido rechazada."
            );
        }

        employeeRepository.save(employee);
    }

    public List<Absence> getAbsenceHistory(Long employeeId){
        Optional<Employee> employee = employeeRepository.findById(employeeId);

        if(!employee.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + employeeId);

        return absenceRepository.findByEmployee(employee.get());
        
    }

    public int calculateDaysAbsence(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("No existe el empleado con el id: " + employeeId));

        List<Absence> history = absenceRepository.findByEmployee(employee);

        int totalDays = history.stream()
                .filter(absence -> absence.getStartDate() != null && absence.getEndDate() != null)
                .mapToInt(absence -> (int) ChronoUnit.DAYS.between(absence.getStartDate(), absence.getEndDate()) + 1)
                .sum();

        employee.setTotalAbsenceDays(totalDays);
        employeeRepository.save(employee);

        return totalDays;
    }

    public List<Absence> getVacationHistory(Long employeeId) {
        Optional<Employee> foundEmployee = employeeRepository.findById(employeeId);

        if (!foundEmployee.isPresent()) {
            throw new IllegalArgumentException("No existe el empleado con el id: " + employeeId);
        }

        Employee employee = foundEmployee.get();
        return absenceRepository.findByEmployeeAndAbsenceType(employee, "Vacaciones");
    }

}
