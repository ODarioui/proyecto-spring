package com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos;

import java.util.ArrayList;
import java.util.List;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Project;

public class ProjectResponseDto {

    private Project project;

    private List<String> goals;

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<String> getGoals() {
        return this.goals;
    }

    public void setGoals(List<String> goals) {
        this.goals = goals;
    }

    public ProjectResponseDto(Project project, List<String> goals) {
        this.project = project;
        this.goals = goals;
    }

    public ProjectResponseDto() {
        this.goals = new ArrayList<>();
    }

}
