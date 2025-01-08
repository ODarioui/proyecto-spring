package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.exceptions.InsufficientPermissionsException;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.User;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ChangePasswordDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ChangeRoleDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.DeleteUserDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserStatusDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.enums.Role;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.UserRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {

        Date creationDate = new Date();
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario registrado con este email");
        } else if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Ya existe un usuario con este nombre de usuario");
        } else if (user.getEmail() == null) {
            throw new IllegalArgumentException("Email obligatorio");
        } else if (user.getUsername() == null) {
            throw new IllegalArgumentException("Username obligatorio");
        }
        user.setRole(Role.USER);
        user.setCreationDate(creationDate);
        return userRepository.save(user);
    }

    @Override
    public User changePassword(String username, ChangePasswordDto changePasswordDto)
            throws UsernameNotFoundException {

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var authority = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst().orElse(null);

        user = userRepository.findByIdOrUsername(changeRoleDto.getId(), changeRoleDto.getUsername());

        if (user == null) {
            throw new IllegalArgumentException("NO se encontro ningun usuario");
        }

        if (changeRoleDto.getRole() == null) {
            throw new IllegalArgumentException("No se ha indicado ningun rol");
        }

        if (authority.contains("ROLE_ADMIN")) {
            role = 4;
        } else if (authority.contains("ROLE_HR")) {
            role = 3;
        } else if (authority.contains("ROLE_EMPLOYEE")) {
            role = 2;
        } else if (authority.contains("ROLE_USER")) {
            role = 1;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElse(null);
        userRepository.delete(user);
    }

    @Override
    public UserDto updateAnyUser(User user) {
        System.out.println(user.getUsername());

        User userUpdate = userRepository.findByIdOrUsername(user.getId(), user.getUsername());
        if (userUpdate.getRole().getValue() > 3 && userUpdate.getRole() != null) {
            throw new InsufficientPermissionsException("No tiene permisios sobre este usuario");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return toDto(user);

    }

    @Override
    public UserDto updateEmployee(User user) {

        User userUpdate = userRepository.findByIdOrUsername(user.getId(), user.getUsername());
        if (userUpdate.getRole().getValue() > 2 && userUpdate.getRole() != null) {
            throw new InsufficientPermissionsException("No tiene permisios sobre este usuario");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return toDto(user);
    }

    @Override
    public UserDto statusUser(UserStatusDto userStatusDto) {

        User userUpdate = userRepository.findByIdOrUsername(userStatusDto.getId(), userStatusDto.getUsername());
        if (userUpdate.getRole().getValue() > 3 && userUpdate.getRole() != null) {
            throw new InsufficientPermissionsException("No tiene permisios sobre este usuario");
        }

        userUpdate.setStatus(userStatusDto.getStatus());

        return toDto(userUpdate);
    }

    @Override
    public UserDto statusEmployee(UserStatusDto userStatusDto) {
        User userUpdate = userRepository.findByIdOrUsername(userStatusDto.getId(), userStatusDto.getUsername());
        if (userUpdate.getRole().getValue() > 2 && userUpdate.getRole() != null) {
            throw new InsufficientPermissionsException("No tiene permisios sobre este usuario");
        }

        userUpdate.setStatus(userStatusDto.getStatus());

        return toDto(userUpdate);
    }

    @Override
    public Map<String, Object> allUsers() {

        List<User> users = userRepository.findAll();
        Map<String, Object> map = new HashMap<>();

        for (User user : users) {
            map.put(user.getId().toString(), toDto(user));
        }

        return map;
    }

    @Override
    public Map<String, Object> allEmployees() {
        List<User> users = userRepository.findAll();
        Map<String, Object> map = new HashMap<>();

        for (User user : users) {
            if (user.getRole().getValue() < 3 || user.getRole() == null) {
                map.put(user.getId().toString(), toDto(user));
            }
        }

        return map;
    }

    @Override
    public UserDto userInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow();

        return toDto(user);

    }

    public static UserDto toDto(User user) {
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

}
