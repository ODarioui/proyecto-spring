package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.exceptions.DepartmentException;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Goal;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Project;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.ProjectResponseDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.ProjectRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Map<String, Object> addProject(Project project) {

        Map<String, Object> map = new HashMap<>();
        ProjectResponseDto projectResponseDto = new ProjectResponseDto();

        try {
            if (project.getId() != null && projectRepository.existsById(project.getId())) {
                throw new DepartmentException("Ya existe un proyecto con este id");
            }

            if (project.getGoals() != null) {
                for (Goal goal : project.getGoals()) {
                    goal.setProject(project);
                    projectResponseDto.getGoals().add(goal.getTitle());
                }
            }

            projectRepository.save(project);
            project.setGoals(null);
            projectResponseDto.setProject(project);
            map.put("project", projectResponseDto);
            return map;
        } catch (Exception e) {
            throw new DepartmentException(e.getMessage());

        }
    }

}
