package com.nttdata.proyecto.rh.gestion_recursos_humanos.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(InsufficientPermissionsException.class)
    public ResponseEntity<String> insufficientPermissionsException(InsufficientPermissionsException e) {
        return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DepartmentException.class)
    public ResponseEntity<ErrorMessage> department(DepartmentException e) {

        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.setTitle("Department Service");
        errorMessage.setMessage(e.getMessage());
        errorMessage.setDate(new Date());
        errorMessage.setStatus(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorMessage> customException(CustomException e) {

        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.setTitle("Department Service");
        errorMessage.setMessage(e.getMessage());
        errorMessage.setDate(new Date());
        errorMessage.setStatus(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(errorMessage);
    }
}
