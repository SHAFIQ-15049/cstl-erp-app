package software.cstl.service.mediators;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Employee;
import software.cstl.domain.EmployeeSalary;
import software.cstl.domain.OverTime;
import software.cstl.domain.enumeration.ActiveStatus;
import software.cstl.domain.enumeration.EmployeeCategory;
import software.cstl.domain.enumeration.EmployeeStatus;
import software.cstl.domain.enumeration.MonthType;
import software.cstl.repository.EmployeeSalaryRepository;
import software.cstl.repository.OverTimeRepository;
import software.cstl.repository.extended.EmployeeExtRepository;
import software.cstl.security.SecurityUtils;
import software.cstl.service.AttendanceSummaryService;
import software.cstl.service.dto.AttendanceSummaryDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Transactional
@AllArgsConstructor
public class OverTimeGenerationService {

    Map<Long, List<AttendanceSummaryDTO>> employeeMapAttendanceSummaries;

    private final EmployeeExtRepository employeeExtRepository;
    private final AttendanceSummaryService attendanceSummaryService;
    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final OverTimeRepository overTimeRepository;

    public List<OverTime> generateOverTime(Integer year, MonthType monthType){
        List<OverTime> overTimes = new ArrayList<>();
        List<Employee> employees = employeeExtRepository.findAllByCategoryAndStatus(EmployeeCategory.WORKER, EmployeeStatus.ACTIVE);

        YearMonth yearMonth = YearMonth.of(year, monthType.ordinal()+1);

        initializeEmployeeSummaries(year, yearMonth);

        for(Employee employee: employees){
            OverTime overTime = new OverTime();
            overTime
                .employee(employee)
                .designation(employee.getDesignation())
                .year(year)
                .month(monthType)
                .executedBy(SecurityUtils.getCurrentUserLogin().get())
                .executedOn(Instant.now());
            calculateEmployeeOverTime(overTime);
            overTimes.add(overTime);
        }

        return overTimeRepository.saveAll(overTimes);
    }

    private void initializeEmployeeSummaries(Integer year, YearMonth yearMonth) {
        LocalDate fromDate = LocalDate.of(year, yearMonth.getMonth(), 1);
        LocalDate toDate = LocalDate.of(year, yearMonth.getMonth(), yearMonth.lengthOfMonth());
        employeeMapAttendanceSummaries = attendanceSummaryService.findAll( fromDate, toDate)
            .stream()
            .collect(Collectors.groupingBy(AttendanceSummaryDTO::getEmployeeId));
    }

    public List<OverTime> regenerateOverTime(Integer year, MonthType monthType){
        overTimeRepository.deleteAllByYearAndMonth(year, monthType);
        overTimeRepository.flush();
        return generateOverTime(year, monthType);
    }

    public OverTime regenerateEmployeeOverTime(Long overTimeId){
        OverTime overTime = overTimeRepository.getOne(overTimeId);
        YearMonth yearMonth = YearMonth.of(overTime.getYear(), overTime.getMonth().ordinal()+1);
        initializeEmployeeSummaries(overTime.getYear(), yearMonth);
        return calculateEmployeeOverTime(overTime);
    }


    private OverTime calculateEmployeeOverTime(OverTime overTime) {
        calculateOverTimeAmount(overTime);
        return overTime;
    }




    private void calculateOverTimeAmount(OverTime overTime) {
        YearMonth yearMonth = YearMonth.of(overTime.getYear(), overTime.getMonth().ordinal()+1);
        LocalDate fromDate = LocalDate.of(overTime.getYear(), yearMonth.getMonth(), 1);
        LocalDate toDate = LocalDate.of(overTime.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth());
        List<AttendanceSummaryDTO> attendanceSummaries = employeeMapAttendanceSummaries.get(overTime.getEmployee().getId());
        Long validOverTimeHour = new Long((attendanceSummaries.size()*2));
        Long totalOverTimeHour = 0L;
        boolean overTimeOverLoaded = false;
        BigDecimal validOverTimeSalary = BigDecimal.ZERO;
        BigDecimal totalOverTimeSalary = BigDecimal.ZERO;
        for(AttendanceSummaryDTO summaryDTO: attendanceSummaries){
            System.out.println(summaryDTO.getOverTime().toHours());
            System.out.println(summaryDTO.getDiff());
            Long overTimeHourOfTheDay = summaryDTO.getOverTime().toHours();
            totalOverTimeHour = totalOverTimeHour+overTimeHourOfTheDay;
            EmployeeSalary employeeSalaryOfTheDay = new EmployeeSalary();
            if(summaryDTO.getEmployeeSalaryId()!=null)
                employeeSalaryOfTheDay = employeeSalaryRepository.getOne(summaryDTO.getEmployeeSalaryId());
            else
                employeeSalaryOfTheDay = employeeSalaryRepository.findByEmployee_IdAndStatus(summaryDTO.getEmployeeId(), ActiveStatus.ACTIVE);
            BigDecimal overTimeAmountPerHour = (employeeSalaryOfTheDay.getBasic().divide(new BigDecimal(208), RoundingMode.HALF_UP)).multiply(new BigDecimal(2));
            totalOverTimeSalary = totalOverTimeSalary.add(overTimeAmountPerHour.multiply(new BigDecimal(overTimeHourOfTheDay)));

            if(!overTimeOverLoaded){
                if(totalOverTimeHour>validOverTimeHour){
                    Long validWithinExcess = totalOverTimeHour - validOverTimeHour;
                    validOverTimeSalary =  validOverTimeSalary.add(overTimeAmountPerHour.multiply(new BigDecimal(validWithinExcess)));
                    overTimeOverLoaded = true;
                }else{
                    validOverTimeSalary = validOverTimeSalary.add(overTimeAmountPerHour.multiply(new BigDecimal(overTimeHourOfTheDay)));
                }
            }
        }
        Long extraOverTimeHour = totalOverTimeHour>validOverTimeHour? totalOverTimeHour - validOverTimeHour: 0L;
        BigDecimal extraOverTimeSalary = totalOverTimeSalary.subtract(validOverTimeSalary);

        overTime.setTotalOverTime(Double.parseDouble(totalOverTimeHour.toString()));
        overTime.setOfficialOverTime(Double.parseDouble((totalOverTimeHour>validOverTimeHour? validOverTimeHour: totalOverTimeHour)+""));
        overTime.setExtraOverTime(Double.parseDouble(extraOverTimeHour.toString()));
        overTime.setTotalAmount(totalOverTimeSalary);
        overTime.setOfficialAmount(totalOverTimeSalary.divide(new BigDecimal(validOverTimeHour), RoundingMode.HALF_UP));
        overTime.setExtraAmount(totalOverTimeSalary.subtract(overTime.getOfficialAmount()));
    }
}
