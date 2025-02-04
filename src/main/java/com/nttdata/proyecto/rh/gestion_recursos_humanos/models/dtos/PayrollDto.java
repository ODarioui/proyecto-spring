package com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos;

import java.util.Date;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.enums.PayrollStauts;

public class PayrollDto {

    private String username;
    private String name;
    private String lastname1;
    private String lastename2;
    private double baseSalary;
    private Double bonuses;
    private Double deductions;
    private Double netSalary;
    private Date paymentDate;
    private String paymentStatus;
    private PayrollStauts stauts;

    public PayrollDto() {
    }

    public PayrollDto(String username, double baseSalary,
            Double bonuses, Double deductions) {
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

    public double getBaseSalary() {
        return this.baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
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

    public Date getPaymentDate() {
        return this.paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentStatus() {
        return this.paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PayrollStauts getStauts() {
        return this.stauts;
    }

    public void setStauts(PayrollStauts stauts) {
        this.stauts = stauts;
    }

}
