package com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
