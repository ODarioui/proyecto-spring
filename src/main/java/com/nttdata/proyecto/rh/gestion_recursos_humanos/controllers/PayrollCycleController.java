package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.PayrollCycle;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ResponseDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.PayrollCycleService;

@RestController
@RequestMapping("/api/v1/payroll-cycle")
public class PayrollCycleController {

    @Autowired
    private PayrollCycleService payrollCycleService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> add(@RequestBody PayrollCycle payrollCycle) {

        ResponseDto responseDto = new ResponseDto();

        responseDto.setDate(new Date());
        responseDto.setMessage("Ciclo de pago creado");
        responseDto.setObject(payrollCycleService.addPayrollCycle(payrollCycle));
        responseDto.setStatus(HttpStatus.CREATED.value());

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

}
