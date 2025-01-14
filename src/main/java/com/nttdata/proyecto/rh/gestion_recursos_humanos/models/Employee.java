package com.nttdata.proyecto.rh.gestion_recursos_humanos.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long departmentId;

    private String position;

    private Date hireDate;
    private double salary;
    private double bonuses;
    private double deductions;
    private Date birthDate;
    private String status;
    private int totalAbsenceDays;
    private int availableVacationDays = 25;
    private int usedVacationDays = 0;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getBonuses() {
        return bonuses;
    }

    public void setBonuses(double bonuses) {
        this.bonuses = bonuses;
    }

    public double getDeductions() {
        return deductions;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long newDepartmentId) {
        this.departmentId = newDepartmentId;
    }

    public int getTotalAbsenceDays() {
        return totalAbsenceDays;
    }

    public void setTotalAbsenceDays(int totalAbsenceDays) {
        this.totalAbsenceDays = totalAbsenceDays;
    }

    public int getAvailableVacationDays() {
        return availableVacationDays;
    }

    public void setAvailableVacationDays(int availableVacationDays) {
        this.availableVacationDays = availableVacationDays;
    }

    public int getUsedVacationDays() {
        return usedVacationDays;
    }

    public void setUsedVacationDays(int usedVacationDays) {
        this.usedVacationDays = usedVacationDays;
    }

}
