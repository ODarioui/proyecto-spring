package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;

import java.util.Date;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Department;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.DepartmentHead;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ProjectToDepartment;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ResponseDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.DepartmentRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.DepartmentService;

@SpringBootTest
public class DepartmentControllerTest {

    private final Department department = new Department();
    private final Long departmentId = 1L;
    @InjectMocks
    private DepartmentController departmentController;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentService departmentService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("null")
    @Test
    public void addDeparmentTest() {
        Mockito.when(departmentService.createDepartment(department)).thenReturn(department);
        ResponseEntity<ResponseDto> response = departmentController.addDeparment(department);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertEquals("Departamento creado correctamente", response.getBody().getMessage());
        assertNotNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    public void updateDeparmentTest() {
        Mockito.when(departmentService.updateDepartment(department)).thenReturn(department);
        ResponseEntity<ResponseDto> response = departmentController.updateDepartment(department);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertEquals("Departamento actualizado", response.getBody().getMessage());
        assertNotNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    public void deleteDeparmentTest() {
        doNothing().when(departmentService).deleteDepartment(department);
        ResponseEntity<ResponseDto> response = departmentController.deleteDepartment(department);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertEquals("Departametno eliminado", response.getBody().getMessage());
        assertNotNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    public void addHeadToDepartmentTest() {
        DepartmentHead departmentHead = new DepartmentHead();
        Mockito.when(departmentService.addHeadToDepartment(departmentHead)).thenReturn(departmentHead);
        ResponseEntity<ResponseDto> response = departmentController.addHeadToDepartment(departmentHead);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertEquals("Responsable de departamento asignado", response.getBody().getMessage());
        assertNotNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    public void listEmployeesDepartmentTest() {
        Mockito.when(departmentService.getEmployeesByDepartment(departmentId)).thenReturn(new HashMap<>());
        ResponseEntity<ResponseDto> response = departmentController.listEmployeesDepartment(departmentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertEquals("Empleados del departamento con id " + departmentId.toString(), response.getBody().getMessage());
        assertNotNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    public void giveProjectTest() {
        ProjectToDepartment projectToDepartment = new ProjectToDepartment();
        Mockito.when(departmentService.addProjectToDepartment(projectToDepartment)).thenReturn(projectToDepartment);
        ResponseEntity<ResponseDto> response = departmentController.giveProject(projectToDepartment);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertEquals("Proyecto asignado", response.getBody().getMessage());
        assertNotNull(response.getBody().getObject());
    }
}
