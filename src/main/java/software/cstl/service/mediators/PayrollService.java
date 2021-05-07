package software.cstl.service.mediators;

import org.apache.commons.lang3.ObjectUtils;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.*;
import software.cstl.domain.enumeration.*;
import software.cstl.repository.*;
import software.cstl.repository.extended.EmployeeExtRepository;
import software.cstl.security.SecurityUtils;
import software.cstl.service.AttendanceSummaryService;
import software.cstl.service.WeekendDateMapService;
import software.cstl.service.WeekendService;
import software.cstl.service.dto.AttendanceSummaryDTO;
import software.cstl.utils.CodeNodeErpUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class PayrollService {

    private Integer totalWorkingDays;
    private Integer totalMonthDays;
    private Integer totalHolidays;
    private List<Holiday> holidays;
    private Integer totalWeekLeave;
    private LocalDate initialDay;
    private LocalDate lastDay;

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
    private final AttendanceSummaryService attendanceSummaryService;
    private final WeekendService weekendService;
    private final LeaveApplicationRepository leaveApplicationRepository;
    private final HolidayRepository holidayRepository;
    private final WeekendDateMapService weekendDateMapService;
    private final OverTimeRepository overTimeRepository;

    public PayrollService(OverTimeRepository overTimeRepository, MonthlySalaryRepository monthlySalaryRepository, MonthlySalaryDtlRepository monthlySalaryDtlRepository, DesignationRepository designationRepository, EmployeeExtRepository employeeExtRepository, DefaultAllowanceRepository defaultAllowanceRepository, FineRepository fineRepository, FinePaymentHistoryRepository finePaymentHistoryRepository, AdvanceRepository advanceRepository, AdvancePaymentHistoryRepository advancePaymentHistoryRepository, AttendanceRepository attendanceRepository, EmployeeSalaryRepository employeeSalaryRepository, PartialSalaryRepository partialSalaryRepository, AttendanceSummaryService attendanceSummaryService, WeekendService weekendService, LeaveApplicationRepository leaveApplicationRepository, HolidayRepository holidayRepository, WeekendDateMapService weekendDateMapService) {
        this.overTimeRepository = overTimeRepository;
        this.monthlySalaryRepository = monthlySalaryRepository;
        this.monthlySalaryDtlRepository = monthlySalaryDtlRepository;
        this.designationRepository = designationRepository;
        this.employeeExtRepository = employeeExtRepository;
        this.defaultAllowanceRepository = defaultAllowanceRepository;
        this.fineRepository = fineRepository;
        this.finePaymentHistoryRepository = finePaymentHistoryRepository;
        this.advanceRepository = advanceRepository;
        this.advancePaymentHistoryRepository = advancePaymentHistoryRepository;
        this.attendanceRepository = attendanceRepository;
        this.employeeSalaryRepository = employeeSalaryRepository;
        this.partialSalaryRepository = partialSalaryRepository;
        this.attendanceSummaryService = attendanceSummaryService;
        this.weekendService = weekendService;
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.holidayRepository = holidayRepository;
        this.weekendDateMapService = weekendDateMapService;
    }

    public MonthlySalary createEmptyMonthlySalaries(MonthlySalary monthlySalary) throws CloneNotSupportedException {
        List<Designation> designations = designationRepository.findAll();
        List<MonthlySalary> monthlySalaries = new ArrayList<>();
        for(Designation designation: designations){
            if(!employeeExtRepository.existsAllByDesignationAndStatus(designation, EmployeeStatus.ACTIVE))
                continue;
            MonthlySalary designationBasedMonthlySalary = new MonthlySalary();
            designationBasedMonthlySalary.setYear(monthlySalary.getYear());
            designationBasedMonthlySalary.setMonth(monthlySalary.getMonth());
            designationBasedMonthlySalary.setDesignation(designation);
            designationBasedMonthlySalary.status(SalaryExecutionStatus.NOT_DONE);
            getEmptyMonthSalaryDtls(designationBasedMonthlySalary);
            monthlySalaryRepository.save(designationBasedMonthlySalary);
            monthlySalaries.add(designationBasedMonthlySalary);
        }
        return monthlySalaries.get(0);
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


    public void createMonthlySalaries(MonthlySalary monthlySalaryParam){
        regenerateMonthlySalaries(monthlySalaryParam);
    }


    public void regenerateMonthlySalaries(MonthlySalary monthlySalaryParam){
        List<MonthlySalary> monthlySalaries = monthlySalaryRepository.findAllByYearAndMonth(monthlySalaryParam.getYear(), monthlySalaryParam.getMonth());
        initializeGlobalValuesForAMonth(monthlySalaryParam);

        monthlySalaries.parallelStream().forEach(monthlySalary -> {
            monthlySalary = monthlySalaryRepository.getOne(monthlySalary.getId());
            monthlySalaryDtlRepository.deleteInBatch(monthlySalary.getMonthlySalaryDtls());
            monthlySalary.setMonthlySalaryDtls(new HashSet<>());
            monthlySalary = getEmptyMonthSalaryDtls(monthlySalary);

            MonthlySalary finalMonthlySalary = monthlySalary;
            monthlySalary.getMonthlySalaryDtls().parallelStream().forEach((monthlySalaryDtl -> {
                assignFine(monthlySalaryDtl);
                assignAdvance(monthlySalaryDtl);
                assignSalaryAndAllowances(finalMonthlySalary, monthlySalaryDtl);
            }));
//            for(MonthlySalaryDtl monthlySalaryDtl: monthlySalary.getMonthlySalaryDtls()){
//                assignFine(monthlySalaryDtl);
//                assignAdvance(monthlySalaryDtl);
//                assignSalaryAndAllowances(monthlySalary, monthlySalaryDtl);
//            }
            monthlySalary.status(SalaryExecutionStatus.DONE);
            monthlySalaryRepository.save(monthlySalary);
        });
/*        for(MonthlySalary monthlySalary: monthlySalaries){
            monthlySalary = monthlySalaryRepository.getOne(monthlySalary.getId());
            monthlySalaryDtlRepository.deleteInBatch(monthlySalary.getMonthlySalaryDtls());
            monthlySalary.setMonthlySalaryDtls(new HashSet<>());
            monthlySalary = getEmptyMonthSalaryDtls(monthlySalary);

            for(MonthlySalaryDtl monthlySalaryDtl: monthlySalary.getMonthlySalaryDtls()){
                assignFine(monthlySalaryDtl);
                assignAdvance(monthlySalaryDtl);
                assignSalaryAndAllowances(monthlySalary, monthlySalaryDtl);
            }
            monthlySalary.status(SalaryExecutionStatus.DONE);
            monthlySalaryRepository.save(monthlySalary);
        }*/


    }

    private void initializeGlobalValuesForAMonth(MonthlySalary monthlySalaryParam) {
        YearMonth yearMonth = YearMonth.of(monthlySalaryParam.getYear(), monthlySalaryParam.getMonth().ordinal()+1);
        this.totalMonthDays = yearMonth.lengthOfMonth();
        this.initialDay = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        this.lastDay = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth());
        this.totalWorkingDays = attendanceSummaryService.findAll(this.initialDay, this.lastDay)
            .stream().filter(a-> a.getAttendanceMarkedAs().equals(AttendanceMarkedAs.R)).collect(Collectors.toList()).size();
        this.totalWeekLeave = weekendDateMapService.findAllWeekendDateMapDTOs(this.initialDay, this.lastDay).size();
        this.holidays = holidayRepository.getOverLappingHolidays(initialDay, lastDay);
        this.totalHolidays = 0;
        for(Holiday holiday: holidays){
            if(holiday.getFrom().isBefore(initialDay) && holiday.getTo().isBefore(lastDay)){
                totalHolidays = totalHolidays+  Period.between(initialDay, holiday.getTo()).getDays();
            }
            else if(holiday.getFrom().isAfter(initialDay) && holiday.getTo().isBefore(lastDay)){
                totalHolidays = totalHolidays + holiday.getTotalDays();
            }
            else if(holiday.getFrom().isAfter(initialDay) && lastDay.isBefore(holiday.getTo())){
                totalHolidays = totalHolidays + Period.between(holiday.getFrom(), lastDay).getDays();
            }
        }
    }

    public MonthlySalaryDtl regenerateMonthlySalaryForAnEmployee(Long monthlySalaryId, Long monthlySalaryDtlId){

        MonthlySalary monthlySalary = monthlySalaryRepository.getOne(monthlySalaryId);
        initializeGlobalValuesForAMonth(monthlySalary);
        MonthlySalaryDtl monthlySalaryDtl = monthlySalaryDtlRepository.getOne(monthlySalaryDtlId);
        assignFine(monthlySalaryDtl);
        assignAdvance(monthlySalaryDtl);
        assignSalaryAndAllowances(monthlySalary, monthlySalaryDtl);
        return monthlySalaryDtlRepository.save(monthlySalaryDtl);
    }

    private void assignSalaryAndAllowances(MonthlySalary monthlySalary, MonthlySalaryDtl monthlySalaryDtl){

        DefaultAllowance defaultAllowance = defaultAllowanceRepository.findDefaultAllowanceByStatus(ActiveStatus.ACTIVE);
        Optional<PartialSalary> partialSalary = partialSalaryRepository.findByEmployeeAndYearAndMonth(monthlySalaryDtl.getEmployee(), monthlySalary.getYear(), monthlySalary.getMonth());

        BigDecimal gross, basic, houseRent, medicalAllowance, convinceAllowance, foodAllowance;
        gross = basic = houseRent = medicalAllowance = foodAllowance = convinceAllowance = BigDecimal.ZERO;


        YearMonth yearMonth = YearMonth.of(monthlySalary.getYear(), monthlySalary.getMonth().ordinal()+1);
        BigDecimal totalMonthDays = new BigDecimal(yearMonth.lengthOfMonth());

        // remove weekends from total working days
        List<Weekend> weekends = weekendService.findAll(WeekendStatus.ACTIVE);
        List<Integer> weekendsInOrdinal = new ArrayList<>();
        for(Weekend weekend: weekends){
            weekendsInOrdinal.add(CodeNodeErpUtils.getWeekDayOrdinalValue(weekend.getDay()));
        }

        LocalDate initialDay = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        LocalDate lastDay = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth());
        Map<LocalDate, AttendanceSummaryDTO> attendanceMap = new HashMap<>();

        List<AttendanceSummaryDTO> attendanceSummaryDTOS = attendanceSummaryService.findAll(monthlySalaryDtl.getEmployee().getId(),initialDay, lastDay );

        for(AttendanceSummaryDTO attendanceSummaryDTO: attendanceSummaryService.findAll(monthlySalaryDtl.getEmployee().getId(),initialDay, lastDay )){
            attendanceMap.put(attendanceSummaryDTO.getAttendanceDate(), attendanceSummaryDTO);
        }
        int leaveCounter = 0;
        int holidayCounter = 0;
        while(!initialDay.isAfter(lastDay)){
            Boolean isWeekend = weekendsInOrdinal.contains(initialDay.getDayOfWeek().getValue());

            AttendanceSummaryDTO attendance = attendanceMap.containsKey(initialDay)? attendanceMap.get(initialDay): null;

            if(attendance!=null){
                EmployeeSalary activeSalaryForTheDay = employeeSalaryRepository.getOne(attendance.getEmployeeSalaryId());
                gross = gross.add(activeSalaryForTheDay.getGross().divide(totalMonthDays, RoundingMode.HALF_UP));
                basic = basic.add(activeSalaryForTheDay.getBasic().divide(totalMonthDays, RoundingMode.HALF_UP));
                houseRent = houseRent.add(activeSalaryForTheDay.getHouseRent().divide(totalMonthDays, RoundingMode.HALF_UP));
                medicalAllowance = medicalAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getMedicalAllowance(), defaultAllowance.getMedicalAllowance()).divide(totalMonthDays, RoundingMode.HALF_UP));
                foodAllowance = foodAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getFoodAllowance(), defaultAllowance.getFoodAllowance()).divide(totalMonthDays, RoundingMode.HALF_UP));
                convinceAllowance = convinceAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getConvinceAllowance(), defaultAllowance.getConvinceAllowance()).divide(totalMonthDays, RoundingMode.HALF_UP));

            }
            Boolean holidayExists = holidayRepository.existsAllByFromLessThanEqualAndToGreaterThanEqual(initialDay, initialDay);
            if(holidayExists)
                holidayCounter +=1;
            Boolean leaveExists = leaveApplicationRepository.existsAllByStatusAndFromLessThanEqualAndToGreaterThanEqual(LeaveApplicationStatus.ACCEPTED, initialDay, initialDay);
            if(leaveExists)
                leaveCounter +=1;
            else if(isWeekend || holidayExists || leaveExists){
                Instant initialDateInstant = initialDay.atStartOfDay(ZoneId.systemDefault()).toInstant();
                EmployeeSalary activeSalaryForTheDay = employeeSalaryRepository.findBySalaryStartDateIsLessThanEqualAndSalaryEndDateGreaterThanEqual(initialDateInstant, initialDateInstant);
                gross = gross.add(activeSalaryForTheDay.getGross().divide(totalMonthDays, RoundingMode.HALF_UP));
                basic = basic.add(activeSalaryForTheDay.getBasic().divide(totalMonthDays, RoundingMode.HALF_UP));
                houseRent = houseRent.add(activeSalaryForTheDay.getHouseRent().divide(totalMonthDays, RoundingMode.HALF_UP));
                medicalAllowance = medicalAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getMedicalAllowance(), defaultAllowance.getMedicalAllowance()).divide(totalMonthDays, RoundingMode.HALF_UP));
                foodAllowance = foodAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getFoodAllowance(), defaultAllowance.getFoodAllowance()).divide(totalMonthDays, RoundingMode.HALF_UP));
                convinceAllowance = convinceAllowance.add(ObjectUtils.defaultIfNull(activeSalaryForTheDay.getConvinceAllowance(), defaultAllowance.getConvinceAllowance()).divide(totalMonthDays, RoundingMode.HALF_UP));

            }

            initialDay = initialDay.plusDays(1);
        }

        Optional<OverTime> overTime = overTimeRepository.findByYearAndMonthAndEmployee(monthlySalary.getYear(), monthlySalary.getMonth(), monthlySalaryDtl.getEmployee());
        if(overTime.isPresent()){
            monthlySalaryDtl.setOverTimeHour(overTime.get().getTotalOverTime());
            monthlySalaryDtl.setOverTimeSalaryHourly(overTime.get().getTotalAmount().divide(new BigDecimal(overTime.get().getTotalOverTime()),RoundingMode.HALF_UP));
            monthlySalaryDtl.setOverTimeSalary(overTime.get().getTotalAmount());
        }else{
            monthlySalaryDtl.setOverTimeHour(0.0);
            monthlySalaryDtl.setOverTimeSalaryHourly(BigDecimal.ZERO);
            monthlySalaryDtl.setOverTimeSalary(BigDecimal.ZERO);
        }


        monthlySalaryDtl.setTotalMonthDays(Integer.parseInt(totalMonthDays.toString()));
        monthlySalaryDtl.setTotalWorkingDays(this.totalWorkingDays);
        monthlySalaryDtl.setWeeklyLeave(this.totalWeekLeave);
        monthlySalaryDtl.setFestivalLeave(this.totalHolidays);
        monthlySalaryDtl.setRegularLeave(0);
        monthlySalaryDtl.setSickLeave(leaveCounter);
        monthlySalaryDtl.setCompensationLeave(0);
        monthlySalaryDtl.setPresent(attendanceMap.size());
        monthlySalaryDtl.setAbsent(this.totalWorkingDays-attendanceMap.size());
        monthlySalaryDtl.setPresentBonus(monthlySalaryDtl.getAbsent()>0 ? BigDecimal.ZERO:  new BigDecimal(150) );
        monthlySalaryDtl.setStampPrice(BigDecimal.ZERO);
        monthlySalaryDtl.setType(partialSalary.isPresent()? PayrollGenerationType.PARTIAL: PayrollGenerationType.FULL);
        monthlySalaryDtl.setGross(gross.subtract(monthlySalaryDtl.getFine()).subtract(monthlySalaryDtl.getAdvance()));
        monthlySalaryDtl.setTotalPayable(monthlySalaryDtl.getGross().add(monthlySalaryDtl.getPresentBonus()).add(monthlySalaryDtl.getStampPrice()));
        monthlySalaryDtl.setBasic(basic);
        monthlySalaryDtl.setHouseRent(houseRent);
        monthlySalaryDtl.setMedicalAllowance(medicalAllowance);
        monthlySalaryDtl.setFoodAllowance(foodAllowance);
        monthlySalaryDtl.setConvinceAllowance(convinceAllowance);
        monthlySalaryDtl.setStatus(SalaryExecutionStatus.DONE);
        monthlySalaryDtl.setExecutedOn(Instant.now());

    }



    public PartialSalary assignPartialSalaryAndAllowances(PartialSalary partialSalary){
        DefaultAllowance defaultAllowance = defaultAllowanceRepository.findDefaultAllowanceByStatus(ActiveStatus.ACTIVE);
        Integer totalWorkingDays = attendanceRepository.countAttendancesByEmployeeAndAttendanceTimeBetween(partialSalary.getEmployee(), Instant.from(partialSalary.getFromDate()) , Instant.from(partialSalary.getToDate()));
        List<Attendance> employeeAttendance = attendanceRepository.findAllByEmployeeAndAttendanceTimeBetween(partialSalary.getEmployee(), Instant.from(partialSalary.getFromDate()), Instant.from(partialSalary.getToDate()));

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
        monthlySalaryDtl.setFine(BigDecimal.ZERO);
        if(fineRepository.existsByEmployeeAndPaymentStatusIn(monthlySalaryDtl.getEmployee(), Arrays.asList( PaymentStatus.PAID))) {  // check whether fine exists
            Fine fine = fineRepository.findFineByEmployeeAndPaymentStatusIn(monthlySalaryDtl.getEmployee(), Arrays.asList( PaymentStatus.PAID));  // if exists, then we fetch the fine
            monthlySalaryDtl.setFine(fine.getAmount());
        }
        else if(fineRepository.existsByEmployeeAndPaymentStatusIn(monthlySalaryDtl.getEmployee(), Arrays.asList(PaymentStatus.IN_PROGRESS, PaymentStatus.NOT_PAID))){  // check whether fine exists
            Fine fine = fineRepository.findFineByEmployeeAndPaymentStatusIn(monthlySalaryDtl.getEmployee(), Arrays.asList(PaymentStatus.NOT_PAID, PaymentStatus.IN_PROGRESS));  // if exists, then we fetch the fine

            // create fine payment history
            if(!finePaymentHistoryRepository.existsByFineAndYearAndMonthType(fine, year, monthType)){
                BigDecimal totalPayableAmount = fine.getAmountLeft().compareTo(fine.getMonthlyFineAmount())>0? fine.getMonthlyFineAmount(): fine.getAmountLeft();
                FinePaymentHistory finePaymentHistory = new FinePaymentHistory()
                    .year(year)
                    .monthType(monthType)
                    .fine(fine)
                    .amount(totalPayableAmount)
                    .beforeFine(fine.getAmountLeft())
                    .afterFine(fine.getAmountLeft().subtract(totalPayableAmount));
                monthlySalaryDtl.setFine(totalPayableAmount);
                finePaymentHistoryRepository.save(finePaymentHistory); // saving history

                fine.setAmountLeft(fine.getAmountLeft().subtract(totalPayableAmount));
                fine.setAmountPaid(fine.getAmountPaid().add(totalPayableAmount));

                if(fine.getAmountPaid().compareTo(fine.getAmount())>=0){ // checking whether the total paid amount is greater or equal than the fine amount
                    fine.setPaymentStatus(PaymentStatus.PAID); // if yes, then we will assign the fine as completed
                }else{
                    fine.setPaymentStatus(PaymentStatus.IN_PROGRESS); // else the fine payment is in progress
                }
                fineRepository.save(fine);
            }else{
                FinePaymentHistory finePaymentHistory = finePaymentHistoryRepository.findByFineAndYearAndMonthType(fine, year, monthType);
                monthlySalaryDtl.setFine(finePaymentHistory.getAmount());
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
        monthlySalaryDtl.setAdvance(BigDecimal.ZERO);
        if(advanceRepository.existsByEmployeeAndPaymentStatusIn(monthlySalaryDtl.getEmployee(), Arrays.asList( PaymentStatus.PAID))) {  // check whether fine exists
            Advance advance = advanceRepository.findByEmployeeAndPaymentStatusIn(monthlySalaryDtl.getEmployee(), Arrays.asList(PaymentStatus.PAID));
            monthlySalaryDtl.setAdvance(advance.getAmount());
        }
        else if(advanceRepository.existsByEmployeeAndPaymentStatusIn(monthlySalaryDtl.getEmployee(), Arrays.asList(PaymentStatus.IN_PROGRESS, PaymentStatus.NOT_PAID))){  // check whether fine exists
            Advance advance = advanceRepository.findByEmployeeAndPaymentStatusIn(monthlySalaryDtl.getEmployee(), Arrays.asList(PaymentStatus.NOT_PAID, PaymentStatus.IN_PROGRESS));  // if exists, then we fetch the fine

            // create advance payment history
            if(!advancePaymentHistoryRepository.existsByAdvanceAndYearAndMonthType(advance, year, monthType)){
                BigDecimal totalPayableAmount = advance.getAmountLeft().compareTo(advance.getMonthlyPaymentAmount())>0? advance.getMonthlyPaymentAmount(): advance.getAmountLeft();
                AdvancePaymentHistory advancePaymentHistory = new AdvancePaymentHistory()
                    .year(year)
                    .advance(advance)
                    .monthType(monthType)
                    .amount(totalPayableAmount)
                    .before(advance.getAmountLeft())
                    .after(advance.getAmountLeft().subtract(totalPayableAmount));

                monthlySalaryDtl.setAdvance(totalPayableAmount);
                advancePaymentHistoryRepository.save(advancePaymentHistory); // saving history

                advance.setAmountLeft(advance.getAmountLeft().subtract(totalPayableAmount));
                advance.setAmountPaid(advance.getAmountPaid().add(totalPayableAmount));

                if(advance.getAmountPaid().compareTo(advance.getAmount())>=0){ // checking whether the total paid amount is greater or equal than the advance amount
                    advance.setPaymentStatus(PaymentStatus.PAID); // if yes, then we will assign the advance as completed
                }else{
                    advance.setPaymentStatus(PaymentStatus.IN_PROGRESS); // else the advance payment is in progress
                }

                advanceRepository.save(advance);
            }else{
                monthlySalaryDtl.setAdvance(advancePaymentHistoryRepository.findByAdvanceAndYearAndMonthType(advance, year, monthType).getAmount());
            }
        }
    }

}
