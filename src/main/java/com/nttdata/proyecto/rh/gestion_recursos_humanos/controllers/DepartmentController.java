package com.nttdata.proyecto.rh.gestion_recursos_humanos.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Department;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.DepartmentHead;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ProjectToDepartment;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ResponseDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.DepartmentService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Secured({ "ROLE_ADMIN", "ROLE_HR" })
@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addDeparment(@RequestBody Department department) {

        ResponseDto responseDto = new ResponseDto();

        departmentService.createDepartment(department);

        responseDto.setDate(new Date());
        responseDto.setMessage("Departamento creado correctamente");
        responseDto.setObject(department);
        responseDto.setStatus(HttpStatus.OK.value());

        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateDepartment(@RequestBody Department department) {
        ResponseDto responseDto = new ResponseDto();

        departmentService.updateDepartment(department);

        responseDto.setDate(new Date());
        responseDto.setMessage("Departamento actualizado");
        responseDto.setObject(department);
        responseDto.setStatus(HttpStatus.OK.value());

        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteDepartment(@RequestBody Department department) {
        ResponseDto responseDto = new ResponseDto();

        departmentService.deleteDepartment(department);

        responseDto.setDate(new Date());
        responseDto.setMessage("Departametno eliminado");
        responseDto.setObject(department);
        responseDto.setStatus(HttpStatus.OK.value());

        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/add-head")
    public ResponseEntity<ResponseDto> addHeadToDepartment(@RequestBody DepartmentHead departmentHead) {

        ResponseDto responseDto = new ResponseDto();

        departmentService.addHeadToDepartment(departmentHead);

        responseDto.setDate(new Date());
        responseDto.setMessage("Responsable de departamento asignado");
        responseDto.setObject(departmentHead);
        responseDto.setStatus(HttpStatus.OK.value());

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/department-employees/{department_id}")
    public ResponseEntity<ResponseDto> listEmployeesDepartment(@PathVariable Long departmentId) {

        ResponseDto responseDto = new ResponseDto();

        responseDto.setDate(new Date());
        responseDto.setMessage("Empleados del departamento con id " + departmentId.toString());
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setObject(departmentService.getEmployeesByDepartment(departmentId));

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/give-project")
    public ResponseEntity<ResponseDto> giveProject(@RequestBody ProjectToDepartment projectToDepartment) {
        ResponseDto responseDto = new ResponseDto();

        responseDto.setDate(new Date());
        responseDto.setMessage("Proyecto asignado");
        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setObject(departmentService.addProjectToDepartment(projectToDepartment));

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
