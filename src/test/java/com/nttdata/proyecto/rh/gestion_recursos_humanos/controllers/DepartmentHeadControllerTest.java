package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.DepartmentHead;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.DepartmentHeadRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.DepartmentHeadService;

@SpringBootTest
public class DepartmentHeadControllerTest {

    private final Long departmentId = 1L;
    private final Long employeeId = 6381L;
    private final Long id = 77L;
    private final DepartmentHead departmentHead = new DepartmentHead();
    private final Optional<DepartmentHead> departmentHeadOptional = Optional.of(departmentHead);

    @InjectMocks
    private DepartmentHeadController departmentHeadController;

    @Mock
    private DepartmentHeadService departmentHeadService;

    @Mock
    private DepartmentHeadRepository departmentRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registerDepartmentHeadTest() {
        Mockito.when(departmentHeadService.registerDepartmentHead(departmentId, employeeId)).thenReturn(departmentHead);
        ResponseEntity<DepartmentHead> response = departmentHeadController.registerDepartmentHead(departmentId,
                employeeId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void ErrorRegisterDepartmentHeadTest() {
        Mockito.when(departmentHeadService.registerDepartmentHead(departmentId, employeeId))
                .thenThrow(IllegalArgumentException.class);
        ResponseEntity<DepartmentHead> response = departmentHeadController.registerDepartmentHead(departmentId,
                employeeId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void getDepartmentHeadTest() {
        Mockito.when(departmentRepository.findByDepartmentId(departmentId)).thenReturn(departmentHeadOptional);
        ResponseEntity<Optional<DepartmentHead>> response = departmentHeadController.getDepartmentHead(departmentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void ErrorgetDepartmentHeadTest() {
        Mockito.when(departmentRepository.findByDepartmentId(departmentId)).thenThrow(new IllegalArgumentException());
        ResponseEntity<Optional<DepartmentHead>> response = departmentHeadController.getDepartmentHead(departmentId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void setDepartmentHeadTest() {
        doNothing().when(departmentHeadService).setDepartmentHead(id, departmentId, employeeId);
        ResponseEntity<String> response = departmentHeadController.setDepartmentHead(id, employeeId,
                departmentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Departamento y posiciones nuevas actualizadas con éxito.", response.getBody());
    }

    @Test
    public void ErrorsetDepartmentHeadTest() {
        doThrow(IllegalArgumentException.class).when(departmentHeadService).setDepartmentHead(id, departmentId,
                employeeId);
        ResponseEntity<String> response = departmentHeadController.setDepartmentHead(id, employeeId,
                departmentId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}
