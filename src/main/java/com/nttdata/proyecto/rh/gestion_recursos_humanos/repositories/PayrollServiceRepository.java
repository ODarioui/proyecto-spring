package com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.PayrollCycle;

@Repository
public interface PayrollServiceRepository extends JpaRepository<PayrollCycle, Long> {

}
