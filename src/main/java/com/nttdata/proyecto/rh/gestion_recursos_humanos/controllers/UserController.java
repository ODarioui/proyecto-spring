package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.User;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ChangePasswordDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.UserService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ChangeRoleDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.DeleteUserDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ResponseDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserStatusDto;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/change-password")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {

        Map<String, Object> map = new HashMap<>();

        User user = userService.changePassword(changePasswordDto);

        if (user != null) {
            map.put("username", user.getUsername());
            map.put("message", "La contraseña se ha modificado correctamente");
        } else {
            map.put("message", "No se ha podido modificar la contraseña");
        }
        return ResponseEntity.ok().body(map);
    }

    @PutMapping("/change-role")
    public ResponseEntity<ResponseDto> changeRole(@RequestBody ChangeRoleDto changeRoleDto) {

        ResponseDto responseDto = new ResponseDto();
        UserDto userDto = null;

        responseDto.setDate(new Date());
        try {
            userDto = userService.changeRole(changeRoleDto);
            responseDto.setObject(userDto);
            responseDto.setMessage("Rol asignado correctamente");
            responseDto.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok().body(responseDto);

        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            responseDto.setObject(null);
            responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDto);

        }

    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteUser(@RequestBody List<DeleteUserDto> usersDelete) {

        ResponseDto responseDto = new ResponseDto();
        responseDto.setDate(new Date());

        try {
            Map<String, Object> map = userService.deleteUsers(usersDelete);
            responseDto.setObject(map);
            responseDto.setMessage("Usuarios eliminados");
            responseDto.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok().body(responseDto);
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDto);
        }

    }

    @DeleteMapping("/self-delete")
    public ResponseEntity<ResponseDto> selfDelete() {

        ResponseDto responseDto = new ResponseDto();
        responseDto.setDate(new Date());
        try {
            userService.selfDelete();
            responseDto.setMessage("Su usuario ha sido eliminado");
            responseDto.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok().body(responseDto);
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAnyUser(@RequestBody User user) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setDate(new Date());
        try {
            UserDto userDto = userService.updateAnyUser(user);
            responseDto.setMessage("Usuario actualizado");
            responseDto.setStatus(HttpStatus.OK.value());
            responseDto.setObject(userDto);
            return ResponseEntity.ok().body(responseDto);
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @Secured("ROLE_HR")
    @PutMapping("/update-employee")
    public ResponseEntity<ResponseDto> updateEmployee(@RequestBody User user) {

        ResponseDto responseDto = new ResponseDto();
        responseDto.setDate(new Date());
        try {
            UserDto userDto = userService.updateEmployee(user);
            responseDto.setMessage("Usuario actualizado");
            responseDto.setStatus(HttpStatus.OK.value());
            responseDto.setObject(userDto);
            return ResponseEntity.ok().body(responseDto);
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/status")
    public ResponseEntity<ResponseDto> userStatus(@RequestBody UserStatusDto userStatusDto) {

        ResponseDto responseDto = new ResponseDto();
        responseDto.setDate(new Date());
        try {
            UserDto userDto = userService.statusUser(userStatusDto);
            responseDto.setMessage("Usuario actualizado");
            responseDto.setStatus(HttpStatus.OK.value());
            responseDto.setObject(userDto);
            return ResponseEntity.ok().body(responseDto);
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @Secured("ROLE_HR")
    @PutMapping("/status-employee")
    public ResponseEntity<ResponseDto> employeeStatus(@RequestBody UserStatusDto userStatusDto) {

        ResponseDto responseDto = new ResponseDto();
        responseDto.setDate(new Date());
        try {
            UserDto userDto = userService.statusEmployee(userStatusDto);
            responseDto.setMessage("Usuario actualizado");
            responseDto.setStatus(HttpStatus.OK.value());
            responseDto.setObject(userDto);
            return ResponseEntity.ok().body(responseDto);
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/all-users")
    public ResponseEntity<ResponseDto> getAllUsers() {

        ResponseDto responseDto = new ResponseDto();
        responseDto.setDate(new Date());
        try {
            Map<String, Object> map = userService.allUsers();
            responseDto.setMessage("Todos los usuarios");
            responseDto.setStatus(HttpStatus.OK.value());
            responseDto.setObject(map);
            return ResponseEntity.ok().body(responseDto);
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @Secured({ "ROLE_HR", "ROLE_ADMIN" })
    @GetMapping("/all-employees")
    public ResponseEntity<ResponseDto> getEmployee() {

        ResponseDto responseDto = new ResponseDto();
        responseDto.setDate(new Date());
        try {
            Map<String, Object> map = userService.allEmployees();
            responseDto.setMessage("Todos los empleados");
            responseDto.setStatus(HttpStatus.OK.value());
            responseDto.setObject(map);
            return ResponseEntity.ok().body(responseDto);
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<ResponseDto> getInfo() {

        ResponseDto responseDto = new ResponseDto();
        responseDto.setDate(new Date());
        try {
            UserDto userDto = userService.userInfo();
            responseDto.setMessage("Informacion de usuario");
            responseDto.setStatus(HttpStatus.OK.value());
            responseDto.setObject(userDto);
            return ResponseEntity.ok().body(responseDto);
        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

}
