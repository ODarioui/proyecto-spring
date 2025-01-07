package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl;

import java.util.Date;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserDto;
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
