package com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Department;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Project;

public class ProjectToDepartment {

    private Project project;
    private Department department;

    public ProjectToDepartment() {
    }

    public ProjectToDepartment(Project project, Department department) {
        this.project = project;
        this.department = department;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

}
