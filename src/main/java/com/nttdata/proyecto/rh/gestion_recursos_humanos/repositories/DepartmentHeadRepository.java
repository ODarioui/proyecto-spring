package com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.DepartmentHead;
import java.util.Optional;

@Repository
public interface DepartmentHeadRepository extends JpaRepository<DepartmentHead, Long> {

    Optional<DepartmentHead> findByDepartmentId(Long departmentId);

    boolean existsById(Long id);

}
