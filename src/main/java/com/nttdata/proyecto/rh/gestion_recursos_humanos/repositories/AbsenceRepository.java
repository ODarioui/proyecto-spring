package com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Absence;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Employee;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long>{
    List<Absence> findByEmployee(Employee employee);

    List<Absence> findByEmployeeAndAbsenceType(Employee employee, String absenceType);
}
