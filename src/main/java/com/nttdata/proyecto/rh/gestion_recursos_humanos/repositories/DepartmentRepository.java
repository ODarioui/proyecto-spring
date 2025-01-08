package com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Department;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{
    Optional<Department> findById(Long id);
}
