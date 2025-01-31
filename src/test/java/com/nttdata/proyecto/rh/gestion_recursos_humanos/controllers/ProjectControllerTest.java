package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Project;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ResponseDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.ProjectService;

@SpringBootTest
public class ProjectControllerTest {

    private final Project project = new Project();
    private final Map<String, Object> map = new HashMap<>();
    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("null")
    @Test
    public void addTest() {
        Mockito.when(projectService.addProject(project)).thenReturn(map);
        ResponseEntity<ResponseDto> response = projectController.add(project);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Proyecto creado", response.getBody().getMessage());
        assertNotNull(response.getBody().getObject());
        assertTrue(response.getBody().getDate() instanceof Date);
    }
}
