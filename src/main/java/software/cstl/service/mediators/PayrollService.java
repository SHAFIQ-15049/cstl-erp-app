package software.cstl.service.mediators;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
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
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
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
    private final AttendanceRepository attendanceRepository;
    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final PartialSalaryRepository partialSalaryRepository;


    public MonthlySalary createEmptyMonthlySalaries(MonthlySalary monthlySalary){
        monthlySalary
            .status(SalaryExecutionStatus.NOT_DONE);
        getEmptyMonthSalaryDtls(monthlySalary);
        return monthlySalaryRepository.save(monthlySalary);
    }

    private MonthlySalary getEmptyMonthSalaryDtls(MonthlySalary monthlySalary){
        List<Employee> employees = employeeExtRepository.findAllByDesignationAndStatus(monthlySalary.getDesignation(), EmployeeStatus.ACTIVE);

        for(Employee employee: employees){
            MonthlySalaryDtl monthlySalaryDtl = new MonthlySalaryDtl();
            monthlySalaryDtl.employee(employee)
                .status(SalaryExecutionStatus.NOT_DONE);
            monthlySalary.addMonthlySalaryDtl(monthlySalaryDtl);
        }
        return monthlySalary;
    }

    public void createMonthlySalaries(MonthlySalary monthlySalary){
        List<Attendance> totalAttendance = attendanceRepository.getAllByAttendanceTimeIsGreaterThanEqualAndAttendanceTimeIsLessThanEqual(monthlySalary.getFromDate(), monthlySalary.getToDate());
        Set<String> attendanceDistinctDays = totalAttendance.stream()
            .map(a-> a.getAttendanceTime().atZone(ZoneId.of("UTC")).toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yy")))
            .collect(Collectors.toSet());
        int totalDays = attendanceDistinctDays.size();
        for(MonthlySalaryDtl monthlySalaryDtl: monthlySalary.getMonthlySalaryDtls()){
            assignSalaryAndAllowances(monthlySalary, monthlySalaryDtl, totalDays);
            assignFine(monthlySalaryDtl);
            assignAdvance(monthlySalaryDtl);
        }
        monthlySalary.status(SalaryExecutionStatus.DONE);
        monthlySalaryRepository.save(monthlySalary);
    }

    public void regenerateMonthlySalaries(MonthlySalary monthlySalary){
        monthlySalaryDtlRepository.deleteInBatch(monthlySalary.getMonthlySalaryDtls());
        monthlySalary.setMonthlySalaryDtls(new HashSet<>());
        monthlySalary = getEmptyMonthSalaryDtls(monthlySalary);

        List<Attendance> totalAttendance = attendanceRepository.getAllByAttendanceTimeIsGreaterThanEqualAndAttendanceTimeIsLessThanEqual(monthlySalary.getFromDate(), monthlySalary.getToDate());
        Set<String> attendanceDistinctDays = totalAttendance.stream()
            .map(a-> a.getAttendanceTime().atZone(ZoneId.of("UTC")).toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yy")))
            .collect(Collectors.toSet());
        int totalDays = attendanceDistinctDays.size();
        for(MonthlySalaryDtl monthlySalaryDtl: monthlySalary.getMonthlySalaryDtls()){
            assignSalaryAndAllowances(monthlySalary, monthlySalaryDtl, totalDays);
            assignFine(monthlySalaryDtl);
            assignAdvance(monthlySalaryDtl);
        }
        monthlySalary.status(SalaryExecutionStatus.DONE);
        monthlySalaryRepository.save(monthlySalary);
    }

    public MonthlySalaryDtl regenerateMonthlySalaryForAnEmployee(Long monthlySalaryId, Long monthlySalaryDtlId){
        MonthlySalary monthlySalary = monthlySalaryRepository.getOne(monthlySalaryId);
        MonthlySalaryDtl monthlySalaryDtl = monthlySalaryDtlRepository.getOne(monthlySalaryDtlId);
        List<Attendance> totalAttendance = attendanceRepository.getAllByAttendanceTimeIsGreaterThanEqualAndAttendanceTimeIsLessThanEqual(monthlySalary.getFromDate(), monthlySalary.getToDate());
        Set<String> attendanceDistinctDays = totalAttendance.stream()
            .map(a-> a.getAttendanceTime().atZone(ZoneId.of("UTC")).toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yy")))
            .collect(Collectors.toSet());
        int totalDays = attendanceDistinctDays.size();
        assignSalaryAndAllowances(monthlySalary, monthlySalaryDtl, totalDays);
        assignFine(monthlySalaryDtl);
        assignAdvance(monthlySalaryDtl);
        return monthlySalaryDtlRepository.save(monthlySalaryDtl);
    }

    private void assignSalaryAndAllowances(MonthlySalary monthlySalary, MonthlySalaryDtl monthlySalaryDtl, int totalDays){
        DefaultAllowance defaultAllowance = defaultAllowanceRepository.findDefaultAllowanceByStatus(ActiveStatus.ACTIVE);
        Optional<PartialSalary> partialSalary = partialSalaryRepository.findByEmployeeAndYearAndMonth(monthlySalaryDtl.getEmployee(), monthlySalary.getYear(), monthlySalary.getMonth());
        Instant fromDateTime = partialSalary.isPresent()?partialSalary.get().getToDate().plus(1, ChronoUnit.MINUTES) : monthlySalary.getFromDate();
        List<Attendance> employeeAttendance = attendanceRepository.getALlByEmployeeEqualsAndAttendanceTimeIsGreaterThanEqualAndAttendanceTimeIsLessThanEqual(monthlySalaryDtl.getEmployee(), fromDateTime, monthlySalary.getToDate());


        BigDecimal gross, basic, houseRent, medicalAllowance, convinceAllowance, foodAllowance;
        gross = basic = houseRent = medicalAllowance = foodAllowance = convinceAllowance = BigDecimal.ZERO;

        BigDecimal totalMonthDays = new BigDecimal(totalDays);

        for(Attendance attendance: employeeAttendance){
            EmployeeSalary activeSalaryForTheDay = attendance.getEmployeeSalary();
            gross = gross.add(activeSalaryForTheDay.getGross().divide(totalMonthDays));
            basic = basic.add(activeSalaryForTheDay.getBasic().divide(totalMonthDays));
            houseRent = houseRent.add(activeSalaryForTheDay.getHouseRent().divide(totalMonthDays));
            medicalAllowance = medicalAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getMedicalAllowance(), defaultAllowance.getMedicalAllowance()).divide(totalMonthDays));
            foodAllowance = foodAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getFoodAllowance(), defaultAllowance.getFoodAllowance()).divide(totalMonthDays));
            convinceAllowance = convinceAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getConvinceAllowance(), defaultAllowance.getConvinceAllowance()).divide(totalMonthDays));
        }

        monthlySalaryDtl.setType(partialSalary.isPresent()? PayrollGenerationType.PARTIAL: PayrollGenerationType.FULL);
        monthlySalaryDtl.setGross(gross);
        monthlySalaryDtl.setBasic(basic);
        monthlySalaryDtl.setHouseRent(houseRent);
        monthlySalaryDtl.setMedicalAllowance(medicalAllowance);
        monthlySalaryDtl.setFoodAllowance(foodAllowance);
        monthlySalaryDtl.setConvinceAllowance(convinceAllowance);

    }

    public PartialSalary assignPartialSalaryAndAllowances(PartialSalary partialSalary){
        DefaultAllowance defaultAllowance = defaultAllowanceRepository.findDefaultAllowanceByStatus(ActiveStatus.ACTIVE);
        Integer totalWorkingDays = attendanceRepository.countAttendancesByEmployeeAndAndConsiderAsAndAttendanceTimeBetween(partialSalary.getEmployee(), ConsiderAsType.REGULAR, Instant.from(partialSalary.getFromDate()) , Instant.from(partialSalary.getToDate()));
        List<Attendance> employeeAttendance = attendanceRepository.findAllByEmployeeAndConsiderAsAndAttendanceTimeBetween(partialSalary.getEmployee(), ConsiderAsType.REGULAR, Instant.from(partialSalary.getFromDate()), Instant.from(partialSalary.getToDate()));

        String notes = partialSalary.getNote()!=null && partialSalary.getNote().length()>0?partialSalary.getNote(): "";
        if(employeeAttendance.size()<totalWorkingDays){
            notes = notes.concat(" Employee has total missing attendance in days: "+ (totalWorkingDays-employeeAttendance.size()));
        }

        BigDecimal gross, basic, houseRent, medicalAllowance, convinceAllowance, foodAllowance;
        gross = basic = houseRent = medicalAllowance = foodAllowance = convinceAllowance = BigDecimal.ZERO;
        BigDecimal totalMonthDays = new BigDecimal(partialSalary.getTotalMonthDays());
        for(Attendance attendance: employeeAttendance){
            EmployeeSalary activeSalaryForTheDay = attendance.getEmployeeSalary();
            gross = gross.add(activeSalaryForTheDay.getGross().divide(totalMonthDays));
            basic = basic.add(activeSalaryForTheDay.getBasic().divide(totalMonthDays));
            houseRent = houseRent.add(activeSalaryForTheDay.getHouseRent().divide(totalMonthDays));
            medicalAllowance = medicalAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getMedicalAllowance(), defaultAllowance.getMedicalAllowance()).divide(totalMonthDays));
            foodAllowance = foodAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getFoodAllowance(), defaultAllowance.getFoodAllowance()).divide(totalMonthDays));
            convinceAllowance = convinceAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getConvinceAllowance(), defaultAllowance.getConvinceAllowance()).divide(totalMonthDays));
        }
        partialSalary.setGross(gross);
        partialSalary.setBasic(basic);
        partialSalary.setHouseRent(houseRent);
        partialSalary.setMedicalAllowance(medicalAllowance);
        partialSalary.setFoodAllowance(foodAllowance);
        partialSalary.setConvinceAllowance(convinceAllowance);

        partialSalary.setStatus(SalaryExecutionStatus.DONE);
        partialSalary.setNote(notes);
        partialSalary.setExecutedBy(SecurityUtils.getCurrentUserLogin().get());
        partialSalary.setExecutedOn(Instant.now());
        return partialSalary;
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
