package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies;

import java.util.List;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Employee;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Payroll;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.EmployeeSalaryDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.FilterPayrollDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.PayrollCostsDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.PayrollDto;

public interface PayrollService {

    public EmployeeSalaryDto calculateSalaryEmployee(Employee employee);

    public PayrollDto generateNewPayroll(Payroll payroll);

    public List<PayrollDto> getPayrolls(FilterPayrollDto filterPayrollDto);

    public List<PayrollDto> getPayrollsEmployee(FilterPayrollDto filterPayrollDto);

    public PayrollCostsDto getReport(FilterPayrollDto filterPayrollDto);

    public List<PayrollDto> setPaymentCycle(Payroll payroll);
}
