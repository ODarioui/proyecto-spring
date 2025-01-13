package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Absence;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Employee;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.AbsenceRequest;
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

    public Absence registerAbsence (AbsenceRequest newAbsence){
        Optional<Employee> employee = employeeRepository.findById(newAbsence.getEmployeeId());

        if(!employee.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + employee.get().getId());
    
        Absence absence = new Absence();
        absence.setEmployee(employee.get());
        absence.setAbsenceType(newAbsence.getAbsenceType());
        absence.setStartDate(newAbsence.getStartDate());
        absence.setEndDate(newAbsence.getEndDate());
        absence.setStatus(newAbsence.getStatus());

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

        List<Absence> history = absenceRepository.findByEmployee(employee.get());

        return history;
    }

    public int calculateDaysAbsence(Long employeeId){
        int totalDays = 0;
        Optional<Employee> employee = employeeRepository.findById(employeeId);

        if(!employee.isPresent())
            throw new IllegalArgumentException("No existe el empleado con el id: " + employeeId);

        List<Absence> history = absenceRepository.findByEmployee(employee.get());

        for (Absence absence : history) {
            long difference = absence.getEndDate().getTime() - absence.getStartDate().getTime();
            totalDays += (difference / (1000*60*60*24))%365;
        }

        employee.get().setTotalAbsenceDays(totalDays);
        employeeRepository.save(employee.get());

        return totalDays;
    }

}
