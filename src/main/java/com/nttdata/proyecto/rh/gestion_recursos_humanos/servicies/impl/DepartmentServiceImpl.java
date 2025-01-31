package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.exceptions.DepartmentException;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Department;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.DepartmentHead;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Employee;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Project;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ProjectToDepartment;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.DepartmentHeadRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.DepartmentRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.EmployeeRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.ProjectRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentHeadRepository departmentHeadRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Department createDepartment(Department department) {
        Department saveDepartment = null;
        try {
            if (department.getId() != null && departmentRepository.existsById(department.getId())) {
                throw new DepartmentException("Ya existe un departamento con este id");
            }
            saveDepartment = departmentRepository.save(department);
        } catch (Exception e) {
            throw new DepartmentException(e.getMessage());
        }
        return saveDepartment;
    }

    @Override
    public Department updateDepartment(Department department) {
        Department updateDepartment = null;
        try {
            updateDepartment = departmentRepository.save(department);
        } catch (Exception e) {
            throw new DepartmentException(e.getMessage());
        }
        return updateDepartment;
    }

    @Override
    public void deleteDepartment(Department department) {
        try {

            if (department.getId() == null || !departmentRepository.existsById(department.getId())) {
                throw new DepartmentException("No existe el departamento que intentas eliminar");
            }
            departmentRepository.delete(department);
        } catch (Exception e) {
            throw new DepartmentException(e.getMessage());
        }
    }

    @Override
    public DepartmentHead addHeadToDepartment(DepartmentHead departmentHead) {
        DepartmentHead saveDepartmentHead = null;
        try {
            saveDepartmentHead = departmentHeadRepository.save(departmentHead);
        } catch (Exception e) {
            throw new DepartmentException(e.getMessage());
        }
        return saveDepartmentHead;
    }

    @Override
    public Map<String, Object> getEmployeesByDepartment(Long departmentId) {
        Optional<Department> department = null;
        Map<String, Object> map = new HashMap<>();
        List<Employee> employees = null;
        try {

            department = departmentRepository.findById(departmentId);
            if (department.isEmpty()) {
                throw new DepartmentException("El departamento con id " + departmentId.toString() + " no existe");
            }
            employees = employeeRepository.findByDepartmentId(departmentId);

            map.put("department", department);
            map.put("employees", employees);
            return map;
        } catch (Exception e) {
            throw new DepartmentException(e.getMessage());
        }
    }

    @Override
    public ProjectToDepartment addProjectToDepartment(ProjectToDepartment projectToDepartment) {
        Department department;
        Project project;
        try {

            department = departmentRepository.findById(projectToDepartment.getDepartment().getId()).orElse(null);

            if (department == null) {
                throw new DepartmentException("El departamneto no existe");
            }

            project = projectRepository.findById(projectToDepartment.getProject().getId()).orElse(null);
            if (project == null) {
                throw new DepartmentException("El proyecto no existe");
            }
            department.getProjects().add(project);

            departmentRepository.save(department);

            projectToDepartment.getDepartment().setName(department.getName());
            projectToDepartment.getProject().setTitle(project.getTitle());

            return projectToDepartment;
        } catch (Exception e) {
            throw new DepartmentException(e.getMessage());
        }
    }

}
