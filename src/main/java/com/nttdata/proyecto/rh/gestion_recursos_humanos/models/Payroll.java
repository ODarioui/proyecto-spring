package com.nttdata.proyecto.rh.gestion_recursos_humanos.models;

import jakarta.persistence.*;

import java.util.Date;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.enums.PayrollStauts;

@Entity
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "payroll_cycle_id")
    private PayrollCycle payrollCycle;

    private Date paymentDate;
    private Double baseSalary;
    private Double bonuses;
    private Double deductions;
    private Double netSalary;
    private String paymentStatus;

    @Enumerated(EnumType.STRING)
    private PayrollStauts stauts;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getPaymentDate() {
        return this.paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
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

    public PayrollCycle getPayrollCycle() {
        return this.payrollCycle;
    }

    public void setPayrollCycle(PayrollCycle payrollCycle) {
        this.payrollCycle = payrollCycle;
    }

}
