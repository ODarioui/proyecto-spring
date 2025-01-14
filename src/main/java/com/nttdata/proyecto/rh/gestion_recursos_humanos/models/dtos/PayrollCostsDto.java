package com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos;

import java.util.List;

public class PayrollCostsDto {

    private List<PayrollDto> payrolls;

    private double totalBaseSalary;

    private double totalDeductions;

    private double totalBounuses;

    private double totalNetSalary;

    private double total;

    public PayrollCostsDto() {
    }

    public PayrollCostsDto(List<PayrollDto> payrolls, double totalBaseSalary, double totalDeductions,
            double totalBounuses, double totalNetSalary, double total) {
        this.payrolls = payrolls;
        this.totalBaseSalary = totalBaseSalary;
        this.totalDeductions = totalDeductions;
        this.totalBounuses = totalBounuses;
        this.totalNetSalary = totalNetSalary;
        this.total = total;
    }

    public List<PayrollDto> getPayrolls() {
        return this.payrolls;
    }

    public void setPayrolls(List<PayrollDto> payrolls) {
        this.payrolls = payrolls;
    }

    public double getTotalBaseSalary() {
        return this.totalBaseSalary;
    }

    public void setTotalBaseSalary(double totalBaseSalary) {
        this.totalBaseSalary = totalBaseSalary;
    }

    public double getTotalDeductions() {
        return this.totalDeductions;
    }

    public void setTotalDeductions(double totalDeductions) {
        this.totalDeductions = totalDeductions;
    }

    public double getTotalBounuses() {
        return this.totalBounuses;
    }

    public void setTotalBounuses(double totalBounuses) {
        this.totalBounuses = totalBounuses;
    }

    public double getTotalNetSalary() {
        return this.totalNetSalary;
    }

    public void setTotalNetSalary(double totalNetSalary) {
        this.totalNetSalary = totalNetSalary;
    }

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
