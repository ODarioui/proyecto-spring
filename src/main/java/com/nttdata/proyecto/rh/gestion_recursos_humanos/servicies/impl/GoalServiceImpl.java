package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.exceptions.DepartmentException;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Goal;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.GoalRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.GoalService;

@Service
public class GoalServiceImpl implements GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Override
    public Goal addGoal(Goal goal) {
        try {
            if (goal.getId() != null && goalRepository.existsById(goal.getId())) {
                throw new DepartmentException("Ya existe un objetivo con este id");
            }

            return goalRepository.save(goal);
        } catch (Exception e) {
            throw new DepartmentException(e.getMessage());

        }
    }

}
