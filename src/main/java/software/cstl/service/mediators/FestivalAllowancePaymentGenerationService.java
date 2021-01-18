package software.cstl.service.mediators;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Employee;
import software.cstl.domain.EmployeeSalary;
import software.cstl.domain.FestivalAllowancePayment;
import software.cstl.domain.FestivalAllowancePaymentDtl;
import software.cstl.domain.enumeration.ActiveStatus;
import software.cstl.domain.enumeration.EmployeeStatus;
import software.cstl.domain.enumeration.EmployeeType;
import software.cstl.domain.enumeration.SalaryExecutionStatus;
import software.cstl.repository.EmployeeSalaryRepository;
import software.cstl.repository.FestivalAllowancePaymentDtlRepository;
import software.cstl.repository.FestivalAllowancePaymentRepository;
import software.cstl.repository.extended.EmployeeExtRepository;
import software.cstl.security.SecurityUtils;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class FestivalAllowancePaymentGenerationService {
    private final FestivalAllowancePaymentRepository festivalAllowancePaymentRepository;
    private final EmployeeExtRepository employeeExtRepository;
    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final FestivalAllowancePaymentDtlRepository festivalAllowancePaymentDtlRepository;

    public FestivalAllowancePayment generateFestivalAllowancePayment(FestivalAllowancePayment festivalAllowancePayment){
        List<Employee> employees = employeeExtRepository.findAllByDesignationAndStatusAndType(festivalAllowancePayment.getDesignation(), EmployeeStatus.ACTIVE, EmployeeType.PERMANENT);
        for(Employee employee: employees){
            calculateEmployeeFestivalPayment(festivalAllowancePayment, employee);
        }
        return festivalAllowancePaymentRepository.save(festivalAllowancePayment);
    }

    private void calculateEmployeeFestivalPayment(FestivalAllowancePayment festivalAllowancePayment, Employee employee) {
        FestivalAllowancePaymentDtl festivalAllowancePaymentDtl = new FestivalAllowancePaymentDtl();
        EmployeeSalary employeeActiveSalary = employeeSalaryRepository.findByEmployeeAndStatus(employee, ActiveStatus.ACTIVE);
        festivalAllowancePaymentDtl.amount(employeeActiveSalary.getBasic())
            .status(SalaryExecutionStatus.DONE)
            .executedOn(Instant.now())
            .employee(employee);
        festivalAllowancePayment.addFestivalAllowancePaymentDtl(festivalAllowancePaymentDtl);
    }

    public FestivalAllowancePayment reGenerateFestivalAllowancePayment(FestivalAllowancePayment festivalAllowancePayment){
        festivalAllowancePaymentDtlRepository.deleteInBatch(festivalAllowancePayment.getFestivalAllowancePaymentDtls());
        festivalAllowancePaymentDtlRepository.flush();
        festivalAllowancePayment.setFestivalAllowancePaymentDtls(new HashSet<>());
        List<Employee> employees = employeeExtRepository.findAllByDesignationAndStatusAndType(festivalAllowancePayment.getDesignation(), EmployeeStatus.ACTIVE, EmployeeType.PERMANENT);
        for(Employee employee: employees){
            calculateEmployeeFestivalPayment(festivalAllowancePayment, employee);
        }
        return festivalAllowancePaymentRepository.save(festivalAllowancePayment);
    }
}
