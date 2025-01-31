package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.User;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ResponseDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserRegisterDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.UserService;

class AuthControllerTest {

    private final UserRegisterDto userRegisterDto = new UserRegisterDto();
    private final User user = new User();
    private final String name = "user";
    private final String lastname1 = "user";
    private final String lastname2 = "user";
    private final String email = "user@email.com";
    private final String username = "user";
    private final String password = "1234";
    private final String phone = "123456789";
    private final String address = "";
    private final Date creationDate = new Date();
    private final String status = "";

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRegisterDto.setName(name);
        userRegisterDto.setLastname1(lastname1);
        userRegisterDto.setLastname2(lastname2);
        userRegisterDto.setEmail(email);
        userRegisterDto.setUsername(username);
        userRegisterDto.setPassword(password);
        userRegisterDto.setPhone(phone);
        userRegisterDto.setAddress(address);
        userRegisterDto.setCreationDate(creationDate);
        userRegisterDto.setStatus(status);
    }

    @SuppressWarnings("null")
    @Test
    void registerTest() {
        Mockito.when(userService.registerUser(userRegisterDto)).thenReturn(user);
        ResponseEntity<ResponseDto> response = authController.registerNewUser(userRegisterDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED.value(), response.getBody().getStatus());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertEquals("Usuario registrado", response.getBody().getMessage());
    }

    @SuppressWarnings("null")
    @Test
    void ErrorregisterTest() {
        Mockito.when(userService.registerUser(userRegisterDto)).thenThrow();
        ResponseEntity<ResponseDto> response = authController.registerNewUser(userRegisterDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }
}
