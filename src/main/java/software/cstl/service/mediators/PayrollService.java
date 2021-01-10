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
            assignFine(monthlySalaryDtl);
            assignAdvance(monthlySalaryDtl);
        }
        monthlySalaryRepository.save(monthlySalary);
    }

    private void assignSalaryAndAllowances(MonthlySalaryDtl monthlySalaryDtl){

    }

/*
* Only assign <b>Fine</b> only if no fine is paid in the current month.
* First we check whether a valid fine really exists or not.
* So, we need to first check the <b>FinePaymentHistory<b/> for the month.
* If not found, then we proceed.
* */
    private void assignFine(MonthlySalaryDtl monthlySalaryDtl){
        Integer year = monthlySalaryDtl.getMonthlySalary().getYear();
        MonthType monthType = monthlySalaryDtl.getMonthlySalary().getMonth();

        if(fineRepository.existsByEmployeeAndPaymentStatusIn(monthlySalaryDtl.getEmployee(), Arrays.asList(PaymentStatus.IN_PROGRESS, PaymentStatus.NOT_PAID))){  // check whether fine exists
            Fine fine = fineRepository.findFineByEmployeeAndPaymentStatusIn(monthlySalaryDtl.getEmployee(), Arrays.asList(PaymentStatus.NOT_PAID, PaymentStatus.IN_PROGRESS));  // if exists, then we fetch the fine

            // create fine payment history
            if(!finePaymentHistoryRepository.existsByFineAndYearAndMonthType(fine, year, monthType)){
                BigDecimal totalPayableAmount = fine.getAmountLeft().compareTo(fine.getMonthlyFineAmount())>0? fine.getMonthlyFineAmount(): fine.getAmountLeft();
                FinePaymentHistory finePaymentHistory = new FinePaymentHistory()
                    .year(year)
                    .monthType(monthType)
                    .amount(totalPayableAmount)
                    .beforeFine(fine.getAmountLeft())
                    .afterFine(fine.getAmountLeft().subtract(totalPayableAmount));

                finePaymentHistoryRepository.save(finePaymentHistory); // saving history

                fine.setAmountLeft(fine.getAmountLeft().subtract(totalPayableAmount));
                fine.setAmountPaid(fine.getAmountPaid().add(totalPayableAmount));

                if(fine.getAmountPaid().compareTo(fine.getAmount())>=0){ // checking whether the total paid amount is greater or equal than the fine amount
                    fine.setPaymentStatus(PaymentStatus.PAID); // if yes, then we will assign the fine as completed
                }else{
                    fine.setPaymentStatus(PaymentStatus.IN_PROGRESS); // else the fine payment is in progress
                }
                fineRepository.save(fine);
            }
        }
    }

    /*
     * Only assign <b>Advance</b> only if no Advance is paid in the current month.
     * First we check whether a valid Advance really exists or not.
     * So, we need to first check the <b>AdvancePaymentHistory<b/> for the month.
     * If not found, then we proceed.
     * */
    private void assignAdvance(MonthlySalaryDtl monthlySalaryDtl){
        Integer year = monthlySalaryDtl.getMonthlySalary().getYear();
        MonthType monthType = monthlySalaryDtl.getMonthlySalary().getMonth();

        if(advanceRepository.existsByEmployeeAndPaymentStatusIn(monthlySalaryDtl.getEmployee(), Arrays.asList(PaymentStatus.IN_PROGRESS, PaymentStatus.NOT_PAID))){  // check whether fine exists
            Advance advance = advanceRepository.findByEmployeeAndPaymentStatusIn(monthlySalaryDtl.getEmployee(), Arrays.asList(PaymentStatus.NOT_PAID, PaymentStatus.IN_PROGRESS));  // if exists, then we fetch the fine

            // create advance payment history
            if(!advancePaymentHistoryRepository.existsByAdvanceAndYearAndMonthType(advance, year, monthType)){
                BigDecimal totalPayableAmount = advance.getAmountLeft().compareTo(advance.getMonthlyPaymentAmount())>0? advance.getMonthlyPaymentAmount(): advance.getAmountLeft();
                AdvancePaymentHistory advancePaymentHistory = new AdvancePaymentHistory()
                    .year(year)
                    .monthType(monthType)
                    .amount(totalPayableAmount)
                    .before(advance.getAmountLeft())
                    .after(advance.getAmountLeft().subtract(totalPayableAmount));

                advancePaymentHistoryRepository.save(advancePaymentHistory); // saving history

                advance.setAmountLeft(advance.getAmountLeft().subtract(totalPayableAmount));
                advance.setAmountPaid(advance.getAmountPaid().add(totalPayableAmount));

                if(advance.getAmountPaid().compareTo(advance.getAmount())>=0){ // checking whether the total paid amount is greater or equal than the advance amount
                    advance.setPaymentStatus(PaymentStatus.PAID); // if yes, then we will assign the advance as completed
                }else{
                    advance.setPaymentStatus(PaymentStatus.IN_PROGRESS); // else the advance payment is in progress
                }

                advanceRepository.save(advance);
            }
        }
    }

}
