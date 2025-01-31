package com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.impl;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.ToDoubleFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.exceptions.CustomException;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Employee;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.Payroll;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.PayrollCycle;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.EmployeeSalaryDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.FilterPayrollDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.PayrollCostsDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos.PayrollDto;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.enums.PayrollStauts;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.EmployeeRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.PayrollCycleRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.repositories.PayrollRepository;
import com.nttdata.proyecto.rh.gestion_recursos_humanos.servicies.PayrollService;

@Service
public class PayrollServiceImpl implements PayrollService {

    private Double DEFAULT_DEDUCTION = 4.7;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private PayrollCycleRepository payrollCycleRepository;

    @Override
    public EmployeeSalaryDto calculateSalaryEmployee(Employee employee) {

        ToDoubleFunction<Employee> calcularSalario = e -> {
            double netSalary = e.getBonuses() + e.getSalary();
            netSalary = netSalary - netSalary * e.getDeductions() * 0.01;
            return netSalary;
        };

        try {
            employee = employeeRepository.findById(employee.getId()).orElse(null);
            if (employee == null) {
                throw new CustomException("No se encontro ningun employee con el id indicado");
            }

            EmployeeSalaryDto employeeSalaryDto = new EmployeeSalaryDto();

            employeeSalaryDto.setBaseSalary(employee.getSalary());
            employeeSalaryDto.setBonuses(employee.getBonuses());
            employeeSalaryDto.setDeductions(employee.getDeductions());
            employeeSalaryDto.setLastename2(employee.getUser().getLastname2());
            employeeSalaryDto.setLastname1(employee.getUser().getLastname1());
            employeeSalaryDto.setName(employee.getUser().getName());
            employeeSalaryDto.setUsername(employee.getUser().getUsername());

            if (employee.getDeductions() == null || employee.getDeductions() == 0) {
                employee.setDeductions(DEFAULT_DEDUCTION);
            }
            double netSalary = (employee.getBonuses() + employee.getSalary());
            netSalary = netSalary - netSalary * employee.getDeductions() * 0.01;
            employeeSalaryDto.setNetSalary(netSalary);

            // Programacion funcional
            employeeSalaryDto.setNetSalary(calcularSalario.applyAsDouble(employee));

            return employeeSalaryDto;
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

    }

    @Override
    public PayrollDto generateNewPayroll(Payroll payroll) {

        try {
            if (payroll.getPaymentDate() == null) {
                throw new CustomException("La fecha de pago es obligatoria");
            }
            Employee employee = employeeRepository.findById(payroll.getEmployee().getId()).orElse(null);
            if (employee == null) {
                throw new CustomException("No se encontro ningun employee con el id indicado");
            }

            if (payroll.getBonuses() == null) {
                payroll.setBonuses(employee.getBonuses());
            } else {
                employee.setBonuses(payroll.getBonuses());
            }
            if (employee.getBonuses() == null) {
                payroll.setBonuses(0.0);

            }

            if (payroll.getDeductions() == null) {
                payroll.setDeductions(employee.getDeductions());
            } else {
                employee.setDeductions(payroll.getDeductions());
            }
            if (employee.getDeductions() == null) {
                employee.setDeductions(DEFAULT_DEDUCTION);
                payroll.setDeductions(DEFAULT_DEDUCTION);
            }

            if (payroll.getBaseSalary() == null) {
                payroll.setBaseSalary((employee.getSalary()));
            } else {
                employee.setSalary(payroll.getBaseSalary());
            }
            if (employee.getSalary() == null) {
                throw new CustomException("Ningun salario definido para este usuario");
            }
            EmployeeSalaryDto employeeSalary = calculateSalaryEmployee(employee);
            payroll.setNetSalary(employeeSalary.getNetSalary());

            if (payroll.getPaymentDate().before(new Date())) {
                payroll.setStauts(PayrollStauts.LATE);
            } else {
                payroll.setStauts(PayrollStauts.ERRING);
            }

            payrollRepository.save(payroll);

            return buildPayrollDto(payroll, employee);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public List<PayrollDto> getPayrolls(FilterPayrollDto filterPayrollDto) {
        try {
            return filtter(filterPayrollDto);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

    }

    @Override
    public List<PayrollDto> getPayrollsEmployee(FilterPayrollDto filterPayrollDto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            filterPayrollDto.setUsername(username);

            return filtter(filterPayrollDto);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

    }

    @Override
    public PayrollCostsDto getReport(FilterPayrollDto filterPayrollDto) {

        try {
            PayrollCostsDto payrollCostsDto = new PayrollCostsDto();
            payrollCostsDto.setTotal(0);
            payrollCostsDto.setTotalBaseSalary(0);
            payrollCostsDto.setTotalBounuses(0);
            payrollCostsDto.setTotalDeductions(0);
            payrollCostsDto.setTotalNetSalary(0);

            List<PayrollDto> list = filtter(filterPayrollDto);

            Double deductions = 0.0;
            Double bonuses = 0.0;
            Double netSalary = 0.0;
            Double baseSalary = 0.0;
            for (PayrollDto payrollDto : list) {
                baseSalary = baseSalary + payrollDto.getBaseSalary();
                bonuses = bonuses + payrollDto.getBonuses();
                netSalary = netSalary + payrollDto.getNetSalary();
                deductions = deductions + payrollDto.getBaseSalary() * payrollDto.getDeductions() * 0.01;
            }

            Double[] result = { 0.0, 0.0, 0.0, 0.0 };
            list.forEach(p -> {
                result[0] = result[0] + p.getBaseSalary() * p.getDeductions() * 0.01;
                result[1] = result[1] + p.getBonuses();
                result[2] = result[2] + p.getNetSalary();
                result[3] = result[3] + p.getBaseSalary();
            });

            payrollCostsDto.setTotalBaseSalary(baseSalary);
            payrollCostsDto.setTotalBounuses(bonuses);
            payrollCostsDto.setTotalDeductions(deductions);
            payrollCostsDto.setTotalNetSalary(netSalary);
            payrollCostsDto.setTotal(bonuses + baseSalary);

            payrollCostsDto.setPayrolls(list);
            return payrollCostsDto;
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public List<PayrollDto> filtter(FilterPayrollDto filterPayrollDto) {
        List<PayrollDto> listDto = new ArrayList<>();

        List<Payroll> list = payrollRepository.findAll();

        // Programacion funcional
        BiPredicate<Object, Object> param1NullOrEqual = (a, b) -> a == null || a.equals(b);
        BiPredicate<Double, Double> param1NullorGt = (a, b) -> a == null || a > b;
        BiPredicate<Double, Double> param1Nullorlt = (a, b) -> a == null || a < b;
        list = list.stream()
                .filter(p -> param1NullOrEqual.test(filterPayrollDto.getEmployee_id(), p.getEmployee().getId()))
                .filter(p -> param1NullOrEqual.test(filterPayrollDto.getLastname1(),
                        p.getEmployee().getUser().getLastname1()))
                .filter(p -> param1NullOrEqual.test(filterPayrollDto.getLastname2(),
                        p.getEmployee().getUser().getLastname2()))
                .filter(p -> param1NullOrEqual.test(filterPayrollDto.getName(), p.getEmployee().getUser().getName()))
                .filter(p -> param1NullOrEqual.test(filterPayrollDto.getUsername(),
                        p.getEmployee().getUser().getUsername()))
                .filter(p -> param1NullorGt.test(filterPayrollDto.getMaxBounses(), p.getBonuses()))
                .filter(p -> param1Nullorlt.test(filterPayrollDto.getMinBounses(), p.getBonuses()))
                .filter(p -> param1NullorGt.test(filterPayrollDto.getMaxDeductions(), p.getDeductions()))
                .filter(p -> param1Nullorlt.test(filterPayrollDto.getMinDeductions(), p.getDeductions()))
                .filter(p -> param1NullorGt.test(filterPayrollDto.getMaxSalary(), p.getBaseSalary()))
                .filter(p -> param1Nullorlt.test(filterPayrollDto.getMinSalary(), p.getBaseSalary()))
                .filter(p -> param1NullorGt.test(filterPayrollDto.getMaxnNtSalary(), p.getNetSalary()))
                .filter(p -> param1Nullorlt.test(filterPayrollDto.getMinNetSalary(), p.getNetSalary()))
                .filter(p -> param1NullOrEqual.test(filterPayrollDto.getMonth(), getMonth(p.getPaymentDate())))
                .filter(p -> param1NullOrEqual.test(filterPayrollDto.getYear(), getYear(p.getPaymentDate())))
                .filter(p -> param1NullOrEqual.test(filterPayrollDto.getPaymentDate(), p.getPaymentDate()))
                .filter(p -> param1NullOrEqual.test(filterPayrollDto.getStauts(), p.getStauts()))
                .toList();

        list = list.stream()
                .filter(payroll -> filterPayrollDto.getEmployee_id() == null
                        || filterPayrollDto.getEmployee_id().equals(payroll.getEmployee().getId()))
                .filter(payroll -> filterPayrollDto.getLastname1() == null
                        || filterPayrollDto.getLastname1().equals(payroll.getEmployee().getUser().getLastname1()))
                .filter(payroll -> filterPayrollDto.getLastname2() == null
                        || filterPayrollDto.getLastname2().equals(payroll.getEmployee().getUser().getLastname2()))
                .filter(payroll -> filterPayrollDto.getName() == null
                        || filterPayrollDto.getName().equals(payroll.getEmployee().getUser().getName()))
                .filter(payroll -> filterPayrollDto.getUsername() == null
                        || filterPayrollDto.getUsername().equals(payroll.getEmployee().getUser().getUsername()))
                .filter(payroll -> filterPayrollDto.getMaxBounses() == null
                        || filterPayrollDto.getMaxBounses() > payroll.getBonuses())
                .filter(payroll -> filterPayrollDto.getMinBounses() == null
                        || filterPayrollDto.getMinBounses() < payroll.getBonuses())
                .filter(payroll -> filterPayrollDto.getMaxDeductions() == null
                        || filterPayrollDto.getMaxDeductions() > payroll.getDeductions())
                .filter(payroll -> filterPayrollDto.getMinDeductions() == null
                        || filterPayrollDto.getMinDeductions() < payroll.getDeductions())
                .filter(payroll -> filterPayrollDto.getMaxSalary() == null
                        || filterPayrollDto.getMaxSalary() > payroll.getBaseSalary())
                .filter(payroll -> filterPayrollDto.getMinSalary() == null
                        || filterPayrollDto.getMinSalary() < payroll.getBaseSalary())
                .filter(payroll -> filterPayrollDto.getMaxnNtSalary() == null
                        || filterPayrollDto.getMaxnNtSalary() > payroll.getNetSalary())
                .filter(payroll -> filterPayrollDto.getMinNetSalary() == null
                        || filterPayrollDto.getMinNetSalary() < payroll.getNetSalary())
                .filter(payroll -> filterPayrollDto.getMonth() == null
                        || filterPayrollDto.getMonth().equals(getMonth(payroll.getPaymentDate())))
                .filter(payroll -> filterPayrollDto.getYear() == null
                        || filterPayrollDto.getYear().equals(getYear(payroll.getPaymentDate())))
                .filter(payroll -> filterPayrollDto.getPaymentDate() == null
                        || filterPayrollDto.getPaymentDate().equals(payroll.getPaymentDate()))
                .filter(payroll -> filterPayrollDto.getStauts() == null
                        || filterPayrollDto.getStauts() == payroll.getStauts())
                .toList();

        for (Payroll payroll : list) {
            listDto.add(buildPayrollDto(payroll, payroll.getEmployee()));
        }

        return listDto;
    }

    private Month getMonth(Date date) {
        LocalDate localDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        return localDate.getMonth();
    }

    private Year getYear(Date date) {
        LocalDate localDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        return Year.of(localDate.getYear());
    }

    private PayrollDto buildPayrollDto(Payroll payroll, Employee employee) {

        return new PayrollDto(
                employee.getUser().getUsername(),
                employee.getUser().getName(),
                employee.getUser().getLastname1(),
                employee.getUser().getLastname2(),
                payroll.getBaseSalary(),
                payroll.getBonuses(),
                payroll.getDeductions(),
                payroll.getNetSalary(),
                payroll.getPaymentDate(),
                payroll.getPaymentStatus(),
                payroll.getStauts());
    }

    @Override
    public List<PayrollDto> setPaymentCycle(Payroll payroll) {
        try {
            if (payroll.getPaymentDate() == null) {
                throw new CustomException("La fecha de pago es obligatoria");
            }
            Employee employee = employeeRepository.findById(payroll.getEmployee().getId()).orElse(null);
            if (employee == null) {
                throw new CustomException("No se encontro ningun employee con el id indicado");
            }

            PayrollCycle payrollCycle = payrollCycleRepository.findById(payroll.getPayrollCycle().getId()).orElse(null);
            if (payrollCycle == null) {
                throw new CustomException("No se encontro el ciclo de pago");
            }

            Date paymentDate = payroll.getPaymentDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(paymentDate);
            int addTime;
            if (payrollCycle.getFrequency().getValue() == 1) {
                addTime = Calendar.DAY_OF_YEAR;
            } else if (payrollCycle.getFrequency().getValue() == 2) {
                addTime = Calendar.WEEK_OF_YEAR;
            } else if (payrollCycle.getFrequency().getValue() == 3) {
                addTime = Calendar.MONTH;
            } else if (payrollCycle.getFrequency().getValue() == 4) {
                addTime = Calendar.YEAR;
            } else {
                addTime = Calendar.MONTH;
            }

            List<PayrollDto> listPayrolls = new ArrayList<>();
            while (calendar.getTime().before(payrollCycle.getEndDate())
                    && calendar.getTime().after(payrollCycle.getStartDate())) {

                payroll.setPaymentDate(calendar.getTime());
                listPayrolls.add(generateNewPayroll(payroll));

                calendar.add(addTime, 1);
            }

            return listPayrolls;
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

}
