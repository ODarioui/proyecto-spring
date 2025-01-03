package com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Employee;
import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsById(Long id);

}
