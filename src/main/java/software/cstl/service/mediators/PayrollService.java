package software.cstl.service.mediators;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.*;
import software.cstl.domain.enumeration.*;
import software.cstl.repository.*;
import software.cstl.repository.extended.EmployeeExtRepository;
import software.cstl.security.SecurityUtils;
import software.cstl.service.MonthlySalaryService;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class PayrollService {

    private final MonthlySalaryRepository monthlySalaryRepository;
    private final MonthlySalaryDtlRepository monthlySalaryDtlRepository;
    private final DesignationRepository designationRepository;
    private final EmployeeExtRepository employeeExtRepository;
    private final DefaultAllowanceRepository defaultAllowanceRepository;
    private final FineRepository fineRepository;
    private final FinePaymentHistoryRepository finePaymentHistoryRepository;
    private final AdvanceRepository advanceRepository;
    private final AdvancePaymentHistoryRepository advancePaymentHistoryRepository;


    public PayrollService(MonthlySalaryRepository monthlySalaryRepository, MonthlySalaryDtlRepository monthlySalaryDtlRepository, DesignationRepository designationRepository, EmployeeExtRepository employeeExtRepository, DefaultAllowanceRepository defaultAllowanceRepository, FineRepository fineRepository, FinePaymentHistoryRepository finePaymentHistoryRepository, AdvanceRepository advanceRepository, AdvancePaymentHistoryRepository advancePaymentHistoryRepository) {
        this.monthlySalaryRepository = monthlySalaryRepository;
        this.monthlySalaryDtlRepository = monthlySalaryDtlRepository;
        this.designationRepository = designationRepository;
        this.employeeExtRepository = employeeExtRepository;
        this.defaultAllowanceRepository = defaultAllowanceRepository;
        this.fineRepository = fineRepository;
        this.finePaymentHistoryRepository = finePaymentHistoryRepository;
        this.advanceRepository = advanceRepository;
        this.advancePaymentHistoryRepository = advancePaymentHistoryRepository;
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

    public void createMonthlySalaries(Integer year, MonthType monthType, Long designationId){
        Designation designation = designationRepository.getOne(designationId);
        MonthlySalary monthlySalary = monthlySalaryRepository.findMonthlySalaryByYearAndMonthAndDesignation(year, monthType, designation);
        for(MonthlySalaryDtl monthlySalaryDtl: monthlySalary.getMonthlySalaryDtls()){
            assignSalaryAndAllowances(monthlySalaryDtl);
        }
    }

    private void assignSalaryAndAllowances(MonthlySalaryDtl monthlySalaryDtl){

    }

    private void assignFine(MonthlySalaryDtl monthlySalaryDtl){
        Integer year = monthlySalaryDtl.getMonthlySalary().getYear();
        MonthType monthType = monthlySalaryDtl.getMonthlySalary().getMonth();
        if(fineRepository.existsByEmployeeAndPaymentStatusIn(monthlySalaryDtl.getEmployee(), Arrays.asList(PaymentStatus.IN_PROGRESS, PaymentStatus.NOT_PAID))){
            Fine fine = fineRepository.findFineByEmployeeAndPaymentStatusIn(monthlySalaryDtl.getEmployee(), Arrays.asList(PaymentStatus.NOT_PAID, PaymentStatus.IN_PROGRESS));

            if(!finePaymentHistoryRepository.existsByFineAndYearAndMonthType(fine, year, monthType)){

            }
        }
    }

}
