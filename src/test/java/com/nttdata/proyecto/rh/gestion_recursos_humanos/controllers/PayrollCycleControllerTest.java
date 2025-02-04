package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.PayrollCycle;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ResponseDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.PayrollCycleService;

@SpringBootTest
class PayrollCycleControllerTest {

    private final PayrollCycle payrollCycle = new PayrollCycle();

    @InjectMocks
    private PayrollCycleController payrollCycleController;

    @Mock
    private PayrollCycleService payrollCycleService;

    @BeforeAll
    static void init() {
        MockitoAnnotations.openMocks(PayrollCycleControllerTest.class);
    }

    @SuppressWarnings("null")
    @Test
    void addTest() {
        Mockito.when(payrollCycleService.addPayrollCycle(payrollCycle)).thenReturn(payrollCycle);
        ResponseEntity<ResponseDto> response = payrollCycleController.add(payrollCycle);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Ciclo de pago creado", response.getBody().getMessage());
        assertNotNull(response.getBody().getObject());
        assertTrue(response.getBody().getDate() instanceof Date);
    }

}
