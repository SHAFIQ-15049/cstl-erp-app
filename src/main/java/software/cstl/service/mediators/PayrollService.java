package software.cstl.service.mediators;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Designation;
import software.cstl.domain.Employee;
import software.cstl.domain.MonthlySalary;
import software.cstl.domain.MonthlySalaryDtl;
import software.cstl.domain.enumeration.EmployeeStatus;
import software.cstl.domain.enumeration.MonthType;
import software.cstl.domain.enumeration.SalaryExecutionStatus;
import software.cstl.repository.DesignationRepository;
import software.cstl.repository.MonthlySalaryDtlRepository;
import software.cstl.repository.MonthlySalaryRepository;
import software.cstl.repository.extended.EmployeeExtRepository;
import software.cstl.security.SecurityUtils;
import software.cstl.service.MonthlySalaryService;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PayrollService {
    private final MonthlySalaryRepository monthlySalaryRepository;
    private final MonthlySalaryDtlRepository monthlySalaryDtlRepository;
    private final DesignationRepository designationRepository;
    private final EmployeeExtRepository employeeExtRepository;

    public PayrollService(MonthlySalaryRepository monthlySalaryRepository, MonthlySalaryDtlRepository monthlySalaryDtlRepository, DesignationRepository designationRepository, EmployeeExtRepository employeeExtRepository) {
        this.monthlySalaryRepository = monthlySalaryRepository;
        this.monthlySalaryDtlRepository = monthlySalaryDtlRepository;
        this.designationRepository = designationRepository;
        this.employeeExtRepository = employeeExtRepository;
    }

    public void createEmptyMonthlySalaries(Integer year, MonthType monthType, Long designationId){
        Designation designation = designationRepository.getOne(designationId);
        MonthlySalary monthlySalary = new MonthlySalary();
        monthlySalary.month(monthType)
            .year(year)
            .designation(designation)
            .monthlySalaryDtls(getEmptyMonthSalaryDtls(designation))
            .status(SalaryExecutionStatus.NOT_DONE);
    }

    private Set<MonthlySalaryDtl> getEmptyMonthSalaryDtls(Designation designation){
        Set<MonthlySalaryDtl> monthlySalaryDtls = new HashSet<>();
        List<Employee> employees = employeeExtRepository.findAllByDesignationAndStatus(designation, EmployeeStatus.ACTIVE);

        for(Employee employee: employees){
            MonthlySalaryDtl monthlySalaryDtl = new MonthlySalaryDtl();
            monthlySalaryDtl.employee(employee)
                .status(SalaryExecutionStatus.NOT_DONE);
            monthlySalaryDtls.add(monthlySalaryDtl);
        }
        return monthlySalaryDtls;
    }

}
