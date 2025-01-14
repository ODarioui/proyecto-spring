package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Goal;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ResponseDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.GoalService;

@RestController
@RequestMapping("/api/v1/goal")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @GetMapping("/get")
    public String get(@RequestParam String param) {
        return new String();
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> add(@RequestBody Goal goal) {
        // TODO: process POST request

        ResponseDto responseDto = new ResponseDto();
        Goal addGoal = goalService.addGoal(goal);

        responseDto.setDate(new Date());
        responseDto.setMessage("Proyecto creado");
        responseDto.setObject(addGoal);
        responseDto.setStatus(HttpStatus.CREATED.value());

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public String update(@PathVariable String id, @RequestBody String entity) {
        // TODO: process PUT request

        return entity;
    }

    @DeleteMapping("/delete")
    public String delete(@PathVariable String id, @RequestBody String entity) {
        // TODO: process PUT request

        return entity;
    }
}
