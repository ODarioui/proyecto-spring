package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.exceptions.CustomException;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.User;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ChangePasswordDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ChangeRoleDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.DeleteUserDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ResponseDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserStatusDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.UserService;

class UserControllerTest {

    private final ChangePasswordDto changePasswordDto = new ChangePasswordDto();
    private final String newPassword = "1234";
    private final String currentPasswoed = "1234";
    private final User user = new User();
    private final UserDto userDto = new UserDto();
    private final Map<String, Object> mapOfObjects = new HashMap<>();

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(UserControllerTest.class);

    }

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        changePasswordDto.setCurretnPassword(currentPasswoed);
        changePasswordDto.setNewPassword(newPassword);
    }

    @SuppressWarnings("null")
    @Test
    void changePasswordTest() {
        Mockito.when(userService.changePassword(changePasswordDto)).thenReturn(user);
        ResponseEntity<Map<String, Object>> response = userController.changePassword(changePasswordDto);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("La contraseña se ha modificado correctamente", response.getBody().get("message"));
    }

    @SuppressWarnings("null")
    @Test
    void ErrorchangePasswordTest() {
        Mockito.when(userService.changePassword(changePasswordDto)).thenReturn(null);
        ResponseEntity<Map<String, Object>> response = userController.changePassword(changePasswordDto);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("No se ha podido modificar la contraseña", response.getBody().get("message"));
    }

    @SuppressWarnings("null")
    @Test
    void changeRolTest() {
        ChangeRoleDto changeRoleDto = new ChangeRoleDto();
        Mockito.when(userService.changeRole(changeRoleDto)).thenReturn(userDto);
        ResponseEntity<ResponseDto> response = userController.changeRole(changeRoleDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Rol asignado correctamente", response.getBody().getMessage());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNotNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    void ErrorchangeRolTest() {
        ChangeRoleDto changeRoleDto = new ChangeRoleDto();
        Mockito.when(userService.changeRole(changeRoleDto)).thenThrow();
        ResponseEntity<ResponseDto> response = userController.changeRole(changeRoleDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    void deleteUsersTest() {
        List<DeleteUserDto> listToDelete = new ArrayList<>();
        Mockito.when(userService.deleteUsers(listToDelete)).thenReturn(mapOfObjects);
        ResponseEntity<ResponseDto> response = userController.deleteUser(listToDelete);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Usuarios eliminados", response.getBody().getMessage());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNotNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    void ErrorDeleteUsersTest() {
        List<DeleteUserDto> listToDelete = new ArrayList<>();
        Mockito.when(userService.deleteUsers(listToDelete)).thenThrow();
        ResponseEntity<ResponseDto> response = userController.deleteUser(listToDelete);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    void selfDeleteTest() {
        doNothing().when(userService).selfDelete();
        ResponseEntity<ResponseDto> response = userController.selfDelete();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Su usuario ha sido eliminado", response.getBody().getMessage());
        assertTrue(response.getBody().getDate() instanceof Date);
    }

    @SuppressWarnings("null")
    @Test
    void ErrorSelfDeleteTest() {
        doThrow(new CustomException("No se pudo eliminar el usuario")).when(userService).selfDelete();
        ResponseEntity<ResponseDto> response = userController.selfDelete();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getDate() instanceof Date);
    }

    @SuppressWarnings("null")
    @Test
    void updateAnyUserTest() {
        Mockito.when(userService.updateAnyUser(user)).thenReturn(userDto);
        ResponseEntity<ResponseDto> response = userController.updateAnyUser(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Usuario actualizado por admin", response.getBody().getMessage());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNotNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    void ErrorupdateAnyUserTest() {
        Mockito.when(userService.updateAnyUser(user)).thenThrow();
        ResponseEntity<ResponseDto> response = userController.updateAnyUser(user);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    void updateEmployeeTest() {
        Mockito.when(userService.updateEmployee(user)).thenReturn(userDto);
        ResponseEntity<ResponseDto> response = userController.updateEmployee(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Usuario actualizado por HR", response.getBody().getMessage());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNotNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    void ErrorupdateEmployeeTest() {
        Mockito.when(userService.updateEmployee(user)).thenThrow();
        ResponseEntity<ResponseDto> response = userController.updateEmployee(user);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    void userStatusTest() {
        UserStatusDto userStatusDto = new UserStatusDto();
        Mockito.when(userService.statusUser(userStatusDto)).thenReturn(userDto);
        ResponseEntity<ResponseDto> response = userController.userStatus(userStatusDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Usuario actualizado", response.getBody().getMessage());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNotNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    void ErrorUserStatusTest() {
        UserStatusDto userStatusDto = new UserStatusDto();
        Mockito.when(userService.statusUser(userStatusDto)).thenThrow();
        ResponseEntity<ResponseDto> response = userController.userStatus(userStatusDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    void employeeStatusTest() {
        UserStatusDto userStatusDto = new UserStatusDto();
        Mockito.when(userService.statusEmployee(userStatusDto)).thenReturn(userDto);
        ResponseEntity<ResponseDto> response = userController.employeeStatus(userStatusDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Usuario actualizado", response.getBody().getMessage());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNotNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    void ErrorEmployeeStatusTest() {
        UserStatusDto userStatusDto = new UserStatusDto();
        Mockito.when(userService.statusEmployee(userStatusDto)).thenThrow();
        ResponseEntity<ResponseDto> response = userController.employeeStatus(userStatusDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    void getAllUsersTest() {
        Mockito.when(userService.allUsers()).thenReturn(new HashMap<>());
        ResponseEntity<ResponseDto> response = userController.getAllUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Todos los usuarios", response.getBody().getMessage());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNotNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    void ErrorgetAllUsersTest() {
        Mockito.when(userService.allUsers()).thenThrow();
        ResponseEntity<ResponseDto> response = userController.getAllUsers();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    void getEmployeeTest() {
        Mockito.when(userService.allEmployees()).thenReturn(new HashMap<>());
        ResponseEntity<ResponseDto> response = userController.getEmployee();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Todos los empleados", response.getBody().getMessage());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNotNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    void ErrorgetEmployeeTest() {
        Mockito.when(userService.allEmployees()).thenThrow();
        ResponseEntity<ResponseDto> response = userController.getEmployee();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    void getInfoTest() {
        Mockito.when(userService.userInfo()).thenReturn(userDto);
        ResponseEntity<ResponseDto> response = userController.getInfo();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Informacion de usuario", response.getBody().getMessage());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNotNull(response.getBody().getObject());
    }

    @SuppressWarnings("null")
    @Test
    void ErrorGetInfoTest() {
        Mockito.when(userService.userInfo()).thenThrow();
        ResponseEntity<ResponseDto> response = userController.getInfo();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getDate() instanceof Date);
        assertNull(response.getBody().getObject());
    }
}
