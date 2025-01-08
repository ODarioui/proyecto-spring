package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.User;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ChangePasswordDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ChangeRoleDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.DeleteUserDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserStatusDto;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String getMethodName() {
        return "hola";
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Map<String, Object> map = new HashMap<>();

        map.put("username", username);

        User user = userService.changePassword(username, changePasswordDto);

        if (user != null) {

            map.put("message", "La contraseña se ha modificado correctamente");
        } else {
            map.put("message", "No se ha podido modificar la contraseña");
        }
        return ResponseEntity.ok().body(map);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/secure")
    public String secureUserMethod() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var authority = authentication.getAuthorities();

        System.out.println(authority);

        return "tienees rol ADMIN";
    }

    @PutMapping("/change-role")
    public ResponseEntity<?> changeRole(@RequestBody ChangeRoleDto ChangeRoleDto) {

        UserDto userDto = null;

        Map<String, Object> map = new HashMap<>();

        userDto = userService.changeRole(ChangeRoleDto);

        map.put("user", userDto);

        return ResponseEntity.ok().body(map);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody List<DeleteUserDto> usersDelete) {

        Map<String, Object> map = userService.deleteUsers(usersDelete);

        return ResponseEntity.ok().body(map);
    }

    @DeleteMapping("/self-delete")
    public ResponseEntity<?> selfDelete() {

        Map<String, Object> map = new HashMap<>();

        map.put("message", "Su usuario ha sido eliminado");

        return ResponseEntity.ok().body(map);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/update")
    public ResponseEntity<?> updateAnyUser(@RequestBody User user) {

        UserDto userDto = userService.updateAnyUser(user);

        return ResponseEntity.ok().body(userDto);
    }

    @Secured("ROLE_HR")
    @PutMapping("/update-employee")
    public ResponseEntity<?> updateEmployee(@RequestBody User user) {

        UserDto userDto = userService.updateEmployee(user);

        return ResponseEntity.ok().body(userDto);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/status")
    public ResponseEntity<?> userStatus(@RequestBody UserStatusDto userStatusDto) {

        UserDto userDto = userService.statusUser(userStatusDto);

        return ResponseEntity.ok().body(userDto);
    }

    @Secured("ROLE_HR")
    @PutMapping("/status-employee")
    public ResponseEntity<?> employeeStatus(@RequestBody UserStatusDto userStatusDto) {

        UserDto userDto = userService.statusEmployee(userStatusDto);

        return ResponseEntity.ok().body(userDto);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {

        Map<String, Object> map = userService.allUsers();
        return ResponseEntity.ok().body(map);
    }

    @Secured({ "ROLE_HR", "ROLE_ADMIN" })
    @GetMapping("/all-employees")
    public ResponseEntity<?> getEmployee() {

        Map<String, Object> map = userService.allEmployees();

        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getInfo() {

        UserDto userDto = userService.userInfo();
        return ResponseEntity.ok().body(userDto);
    }

}
