package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Employee;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Payroll;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.FilterPayrollDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ResponseDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.PayrollService;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/payroll")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    @Secured({ "ROLE_HR", "ROLE_ADMIN" })
    @GetMapping("/calculate-salary")
    public ResponseEntity<ResponseDto> calcSalaryEmployee(@RequestBody Employee employee) {

        ResponseDto responseDto = new ResponseDto();

        responseDto.setObject(payrollService.calculateSalaryEmployee(employee));
        responseDto.setDate(new Date());
        responseDto.setMessage("Salary");
        responseDto.setStatus(HttpStatus.OK.value());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Secured({ "ROLE_HR", "ROLE_ADMIN" })
    @PostMapping("/new-payroll")
    public ResponseEntity<ResponseDto> generatePayroll(@RequestBody Payroll payroll) {

        ResponseDto responseDto = new ResponseDto();
        responseDto.setObject(payrollService.generateNewPayroll(payroll));
        responseDto.setMessage("New payroll");
        responseDto.setStatus(HttpStatus.CREATED.value());
        responseDto.setDate(new Date());

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Secured({ "ROLE_HR", "ROLE_ADMIN" })
    @GetMapping("/get")
    public ResponseEntity<ResponseDto> filtterPayrolls(@RequestBody FilterPayrollDto filterPayrollDto) {

        ResponseDto responseDto = new ResponseDto();
        responseDto.setObject(payrollService.getPayrolls(filterPayrollDto));
        responseDto.setDate(new Date());
        responseDto.setMessage("List of payrolls");
        responseDto.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/payroll")
    public ResponseEntity<ResponseDto> getPayrollsEmployee(@RequestBody FilterPayrollDto filterPayrollDto) {

        ResponseDto responseDto = new ResponseDto();
        responseDto.setObject(payrollService.getPayrolls(filterPayrollDto));
        responseDto.setDate(new Date());
        responseDto.setMessage("List of payrolls");
        responseDto.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Secured({ "ROLE_HR", "ROLE_ADMIN" })
    @GetMapping("/costs")
    public ResponseEntity<ResponseDto> getCosts(@RequestBody FilterPayrollDto filterPayrollDto) {

        ResponseDto responseDto = new ResponseDto();
        responseDto.setObject(payrollService.getReport(filterPayrollDto));
        responseDto.setDate(new Date());
        responseDto.setMessage("List of payrolls");
        responseDto.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/payment-cycle")
    public ResponseEntity<ResponseDto> putMethodName(@RequestBody Payroll payroll) {

        ResponseDto responseDto = new ResponseDto();
        responseDto.setObject(payrollService.setPaymentCycle(payroll));
        responseDto.setDate(new Date());
        responseDto.setMessage("List of payrolls");
        responseDto.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
    }
}
