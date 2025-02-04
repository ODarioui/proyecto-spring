package com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos;

import java.sql.Date;
import java.time.Month;
import java.time.Year;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.enums.PayrollStauts;

public class FilterPayrollDto {

    private String username;
    private String name;
    private String lastname1;
    private String lastname2;
    private Long employeeId;
    private Month month;
    private Year year;
    private Date paymentDate;
    private PayrollStauts stauts;
    private Double minSalary;
    private Double maxSalary;
    private Double minBounses;
    private Double maxBounses;
    private Double minDeductions;
    private Double maxDeductions;
    private Double minNetSalary;
    private Double maxnNtSalary;

    public FilterPayrollDto() {
    }

    public FilterPayrollDto(String username, Long employeeId) {
        this.username = username;
        this.employeeId = employeeId;
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

    public String getLastname2() {
        return this.lastname2;
    }

    public void setLastname2(String lastname2) {
        this.lastname2 = lastname2;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public void setemployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Month getMonth() {
        return this.month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Year getYear() {
        return this.year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Date getPaymentDate() {
        return this.paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PayrollStauts getStauts() {
        return this.stauts;
    }

    public void setStauts(PayrollStauts stauts) {
        this.stauts = stauts;
    }

    public Double getMinSalary() {
        return this.minSalary;
    }

    public void setMinSalary(Double minSalary) {
        this.minSalary = minSalary;
    }

    public Double getMaxSalary() {
        return this.maxSalary;
    }

    public void setMaxSalary(Double maxSalary) {
        this.maxSalary = maxSalary;
    }

    public Double getMinBounses() {
        return this.minBounses;
    }

    public void setMinBounses(Double minBounses) {
        this.minBounses = minBounses;
    }

    public Double getMaxBounses() {
        return this.maxBounses;
    }

    public void setMaxBounses(Double maxBounses) {
        this.maxBounses = maxBounses;
    }

    public Double getMinDeductions() {
        return this.minDeductions;
    }

    public void setMinDeductions(Double minDeductions) {
        this.minDeductions = minDeductions;
    }

    public Double getMaxDeductions() {
        return this.maxDeductions;
    }

    public void setMaxDeductions(Double maxDeductions) {
        this.maxDeductions = maxDeductions;
    }

    public Double getMinNetSalary() {
        return this.minNetSalary;
    }

    public void setMinNetSalary(Double minNetSalary) {
        this.minNetSalary = minNetSalary;
    }

    public Double getMaxnNtSalary() {
        return this.maxnNtSalary;
    }

    public void setMaxnNtSalary(Double maxnNtSalary) {
        this.maxnNtSalary = maxnNtSalary;
    }

}
