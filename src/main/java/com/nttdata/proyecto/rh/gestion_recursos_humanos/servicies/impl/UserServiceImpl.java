package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.exceptions.CustomException;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.exceptions.InsufficientPermissionsException;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.User;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ChangePasswordDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ChangeRoleDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.DeleteUserDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserRegisterDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserStatusDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.enums.Role;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.UserRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.UserService;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.util.Security;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private Security security;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, Security security) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.security = security;
    }

    @Override
    public User registerUser(UserRegisterDto userR) {

        userR.setPassword(passwordEncoder.encode(userR.getPassword()));
        Date creationDate = new Date();
        User user = toUser(userR);
        user.setRole(Role.USER);
        user.setCreationDate(creationDate);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new CustomException("Ya existe un usuario registrado con este email");
        } else if (userRepository.existsByUsername(user.getUsername())) {
            throw new CustomException("Ya existe un usuario con este nombre de usuario");
        } else if (user.getEmail() == null) {
            throw new CustomException("Email obligatorio");
        } else if (user.getUsername() == null) {
            throw new CustomException("Username obligatorio");
        }

        return userRepository.save(user);
    }

    @Override
    public User changePassword(ChangePasswordDto changePasswordDto)
            throws UsernameNotFoundException {

        String username = security.getUsernameLogged();
        User user = userRepository.findByUsername(username).orElseThrow();

        if (passwordEncoder.matches(changePasswordDto.getCurretnPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public UserDto changeRole(ChangeRoleDto changeRoleDto) {

        User user = null;
        int role = 0;
        List<String> authority = security.userAuthority();

        user = userRepository.findByIdOrUsername(changeRoleDto.getId(), changeRoleDto.getUsername());

        if (user == null) {
            throw new IllegalArgumentException("NO se encontro ningun usuario");
        }

        if (changeRoleDto.getRole() == null) {
            throw new IllegalArgumentException("No se ha indicado ningun rol");
        }

        if (authority.contains("ROLE_USER")) {
            role = 1;
        } else if (authority.contains("ROLE_ADMIN")) {
            role = 4;
        } else if (authority.contains("ROLE_HR")) {
            role = 3;
        }
        if (authority.contains("ROLE_EMPLOYEE")) {
            role = 2;
        }

        if (role < 3) {
            throw new InsufficientPermissionsException("No tienes permisos suficentes para cometer esta accion");
        } else if ((role == 3 && changeRoleDto.getRole().getValue() < 3)
                || (role == 4 && changeRoleDto.getRole().getValue() < 4)) {
            user.setRole(changeRoleDto.getRole());
            user = userRepository.save(user);
        }

        return toDto(user);
    }

    @Override
    public Map<String, Object> deleteUsers(List<DeleteUserDto> usersDelete) {

        Integer count = 1;
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> userMap;
        User user = null;

        for (DeleteUserDto userDelete : usersDelete) {
            userMap = new HashMap<>();
            userMap.put("User", userDelete);
            try {
                user = userRepository.findByIdOrUsername(userDelete.getId(), userDelete.getUsername());
                userRepository.delete(user);
                userMap.put("message", "Usuario eliminado");

            } catch (Exception e) {
                userMap.put("message", "No se pudo eliminar este usuario");
                userMap.put("Error", e.getMessage());

            }
            map.put(count.toString(), userMap);
            count++;
        }

        return map;
    }

    @Override
    public void selfDelete() {
        String username = security.getUsernameLogged();
        try {
            User user = userRepository.findByUsername(username).orElseThrow();
            userRepository.delete(user);

        } catch (Exception e) {
            throw new CustomException("No se pudo eliminar");
        }
    }

    @Override
    public UserDto updateAnyUser(User user) {

        User userUpdate = userRepository.findByIdOrUsername(user.getId(), user.getUsername());

        // implementacion programación funcional
        Predicate<Role> isAdmin = r -> r == null || r.getValue() > 3;
        if (isAdmin.test(userUpdate.getRole())) {
            throw new InsufficientPermissionsException("No tiene permisios sobre este usuario ");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return toDto(user);

    }

    @Override
    public UserDto updateEmployee(User user) {

        User userUpdate = userRepository.findByIdOrUsername(user.getId(), user.getUsername());

        // implementacion programación funcional
        Predicate<Role> isHR = r -> r == null || r.getValue() > 2;
        if (isHR.test(userUpdate.getRole())) {
            throw new InsufficientPermissionsException("No tiene permisios sobre  este usuario");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return toDto(user);
    }

    @Override
    public UserDto statusUser(UserStatusDto userStatusDto) {

        User userUpdate = userRepository.findByIdOrUsername(userStatusDto.getId(), userStatusDto.getUsername());

        // implementacion programación funcional
        Predicate<Role> isAdmin = r -> r == null || r.getValue() > 3;
        if (isAdmin.test(userUpdate.getRole())) {
            throw new InsufficientPermissionsException("No tiene permisios sobre este usuario");
        }

        userUpdate.setStatus(userStatusDto.getStatus());
        userRepository.save(userUpdate);

        return toDto(userUpdate);
    }

    @Override
    public UserDto statusEmployee(UserStatusDto userStatusDto) {
        User userUpdate = userRepository.findByIdOrUsername(userStatusDto.getId(), userStatusDto.getUsername());

        // implementacion programación funcional
        Predicate<Role> isAdmin = r -> r == null || r.getValue() > 2;
        if (isAdmin.test(userUpdate.getRole())) {
            throw new InsufficientPermissionsException("No tiene permisios sobre este usuario");
        }

        userUpdate.setStatus(userStatusDto.getStatus());
        userRepository.save(userUpdate);

        return toDto(userUpdate);
    }

    @Override
    public Map<String, Object> allUsers() {

        List<User> users = userRepository.findAll();

        Map<String, Object> map;

        // implementacion programación funcional
        map = users.stream()
                .collect(Collectors.toMap(u -> u.getId().toString(), this::toDto));

        return map;
    }

    @Override
    public Map<String, Object> allEmployees() {
        List<User> users = userRepository.findAll();
        Map<String, Object> map;

        // implementacion programación funcional
        map = users.stream()
                .filter(u -> u.getRole() == null || u.getRole().getValue() < 3)
                .collect(Collectors.toMap(u -> u.getId().toString(), this::toDto));

        return map;
    }

    @Override
    public UserDto userInfo() {
        String username = security.getUsernameLogged();

        User user = userRepository.findByUsername(username).orElseThrow();

        return toDto(user);

    }

    private UserDto toDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setAddress(user.getAddress());
        userDto.setCreationDate(user.getCreationDate());
        userDto.setEmail(user.getEmail());
        userDto.setLastname1(user.getLastname1());
        userDto.setLastname2(user.getLastname2());
        userDto.setName(user.getName());
        userDto.setRole(user.getRole());
        userDto.setPhone(user.getPhone());
        userDto.setStatus(user.getStatus());
        userDto.setUsername(user.getUsername());

        return userDto;

    }

    public static User toUser(UserRegisterDto userDto) {
        User user = new User();

        user.setName(userDto.getName());
        user.setLastname1(userDto.getLastname1());
        user.setLastname2(userDto.getLastname2());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setPhone(userDto.getPhone());
        user.setAddress(userDto.getAddress());
        user.setStatus(userDto.getStatus());

        return user;
    }
}
