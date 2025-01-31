package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Goal;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ResponseDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.GoalService;

public class GoalControllerTest {

    private final Goal goal = new Goal();

    @InjectMocks
    private GoalController goalController;

    @Mock
    private GoalService goalService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("null")
    @Test
    public void addTest() {
        Mockito.when(goalService.addGoal(goal)).thenReturn(goal);
        ResponseEntity<ResponseDto> response = goalController.add(goal);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Proyecto creado", response.getBody().getMessage());
        assertNotNull(response.getBody().getObject());
        assertTrue(response.getBody().getDate() instanceof Date);
    }
}
