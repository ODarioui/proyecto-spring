package com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos;

public class EmployeeSalaryDto {

    private String username;
    private String name;
    private String lastname1;
    private String lastename2;
    private Double baseSalary;
    private Double bonuses;
    private Double deductions;
    private Double netSalary;

    public EmployeeSalaryDto() {
    }

    public EmployeeSalaryDto(String username, Double baseSalary, Double bonuses, Double deductions) {
        this.username = username;
        this.baseSalary = baseSalary;
        this.bonuses = bonuses;
        this.deductions = deductions;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname1() {
        return this.lastname1;
    }

    public void setLastname1(String lastname1) {
        this.lastname1 = lastname1;
    }

    public String getLastename2() {
        return this.lastename2;
    }

    public void setLastename2(String lastename2) {
        this.lastename2 = lastename2;
    }

    public Double getBaseSalary() {
        return this.baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Double getBonuses() {
        return this.bonuses;
    }

    public void setBonuses(Double bonuses) {
        this.bonuses = bonuses;
    }

    public Double getDeductions() {
        return this.deductions;
    }

    public void setDeductions(Double deductions) {
        this.deductions = deductions;
    }

    public Double getNetSalary() {
        return this.netSalary;
    }

    public void setNetSalary(Double netSalary) {
        this.netSalary = netSalary;
    }

}
