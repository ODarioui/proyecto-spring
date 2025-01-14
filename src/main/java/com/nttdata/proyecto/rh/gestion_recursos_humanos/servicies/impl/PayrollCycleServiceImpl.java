package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.exceptions.CustomException;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.PayrollCycle;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.PayrollCycleRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.PayrollCycleService;

@Service
public class PayrollCycleServiceImpl implements PayrollCycleService {

    @Autowired
    private PayrollCycleRepository payrollCycleRepository;

    @Override
    public PayrollCycle addPayrollCycle(PayrollCycle payrollCycle) {
        try {
            return payrollCycleRepository.save(payrollCycle);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

}
