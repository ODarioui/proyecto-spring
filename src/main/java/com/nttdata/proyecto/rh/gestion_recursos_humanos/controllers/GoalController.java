package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Goal;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ResponseDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.GoalService;

@RestController
@RequestMapping("/api/v1/goal")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> add(@RequestBody Goal goal) {

        ResponseDto responseDto = new ResponseDto();
        Goal addGoal = goalService.addGoal(goal);

        responseDto.setDate(new Date());
        responseDto.setMessage("Proyecto creado");
        responseDto.setObject(addGoal);
        responseDto.setStatus(HttpStatus.CREATED.value());

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

}
