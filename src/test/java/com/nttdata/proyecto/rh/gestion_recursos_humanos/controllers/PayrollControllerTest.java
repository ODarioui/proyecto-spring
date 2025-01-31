package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Employee;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Payroll;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.EmployeeSalaryDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.FilterPayrollDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.PayrollCostsDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.PayrollDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ResponseDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.PayrollService;

@SpringBootTest
public class PayrollControllerTest {

    private final Employee employee = new Employee();
    private final EmployeeSalaryDto employeeSalaryDto = new EmployeeSalaryDto();
    private final Payroll payroll = new Payroll();
    private final PayrollDto payrollDto = new PayrollDto();
    private final FilterPayrollDto filterPayrollDto = new FilterPayrollDto();
    private final List<PayrollDto> listOfPayrolls = new ArrayList<>();
    private final PayrollCostsDto payrollCostsDto = new PayrollCostsDto();
    @InjectMocks
    private PayrollController payrollController;

    @Mock
    private PayrollService payrollService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("null")
    @Test
    public void calcSalaryEmployeeTest() {
        Mockito.when(payrollService.calculateSalaryEmployee(employee)).thenReturn(employeeSalaryDto);
        ResponseEntity<ResponseDto> response = payrollController.calcSalaryEmployee(employee);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Salary", response.getBody().getMessage());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNotNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    public void generatePayrollTest() {
        Mockito.when(payrollService.generateNewPayroll(payroll)).thenReturn(payrollDto);
        ResponseEntity<ResponseDto> response = payrollController.generatePayroll(payroll);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("New payroll", response.getBody().getMessage());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNotNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    public void filtterPayrollsTest() {
        Mockito.when(payrollService.getPayrolls(filterPayrollDto)).thenReturn(listOfPayrolls);
        ResponseEntity<ResponseDto> response = payrollController.filtterPayrolls(filterPayrollDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Lista de nominas", response.getBody().getMessage());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNotNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    public void getPayrollsEmployeeTest() {
        Mockito.when(payrollService.getPayrollsEmployee(filterPayrollDto)).thenReturn(listOfPayrolls);
        ResponseEntity<ResponseDto> response = payrollController.getPayrollsEmployee(filterPayrollDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Lista de nominas", response.getBody().getMessage());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNotNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    public void getCostsTest() {
        Mockito.when(payrollService.getReport(filterPayrollDto)).thenReturn(payrollCostsDto);
        ResponseEntity<ResponseDto> response = payrollController.getCosts(filterPayrollDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Lista de nominas", response.getBody().getMessage());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNotNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    public void paymentCycleTest() {
        Mockito.when(payrollService.setPaymentCycle(payroll)).thenReturn(listOfPayrolls);
        ResponseEntity<ResponseDto> response = payrollController.paymentCycle(payroll);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Lista de nominas", response.getBody().getMessage());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNotNull(response.getBody().getObject());
    }
}
