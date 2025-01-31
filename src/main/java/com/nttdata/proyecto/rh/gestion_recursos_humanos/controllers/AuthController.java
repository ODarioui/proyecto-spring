package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ResponseDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.UserRegisterDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> registerNewUser(@RequestBody UserRegisterDto user) {

        ResponseDto responseDto = new ResponseDto();

        try {

            responseDto.setDate(new Date());
            responseDto.setMessage("Usuario registrado");
            responseDto.setObject(userService.registerUser(user));
            responseDto.setStatus(HttpStatus.CREATED.value());
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

        } catch (Exception e) {
            responseDto.setMessage(e.getMessage());
            responseDto.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }

    }
}
