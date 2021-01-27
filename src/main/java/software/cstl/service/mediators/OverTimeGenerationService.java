package software.cstl.service.mediators;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Employee;
import software.cstl.domain.EmployeeSalary;
import software.cstl.domain.OverTime;
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
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class OverTimeGenerationService {
    private final EmployeeExtRepository employeeExtRepository;
    private final AttendanceSummaryService attendanceSummaryService;
    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final OverTimeRepository overTimeRepository;

    public List<OverTime> generateOverTime(Integer year, MonthType monthType, Long designationId, Instant fromDate, Instant toDate){
        List<OverTime> overTimes = new ArrayList<>();
        List<Employee> employees = employeeExtRepository.findAllByDesignation_IdAndStatus(designationId, EmployeeStatus.ACTIVE);
        for(Employee employee: employees){
            OverTime overTime = new OverTime();
            overTime
                .employee(employee)
                .designation(employee.getDesignation())
                .year(year)
                .month(monthType)
                .fromDate(fromDate)
                .toDate(toDate)
                .executedBy(SecurityUtils.getCurrentUserLogin().get())
                .executedOn(Instant.now());
            calculateEmployeeOverTime(overTime);
            overTimes.add(overTime);
        }

        return overTimeRepository.saveAll(overTimes);
    }

    public List<OverTime> regenerateOverTime(Integer year, MonthType monthType, Long designationId, Instant fromDate, Instant toDate){
        overTimeRepository.deleteOverTimeByYearAndMonthAndDesignation_Id(year, monthType, designationId);
        overTimeRepository.flush();
        return generateOverTime(year, monthType, designationId, fromDate, toDate);
    }

    public OverTime regenerateEmployeeOverTime(Long overTimeId){
        OverTime overTime = overTimeRepository.getOne(overTimeId);
        return calculateEmployeeOverTime(overTime);
    }


    private OverTime calculateEmployeeOverTime(OverTime overTime) {
        calculateOverTimeAmount(overTime);
        return overTime;
    }




    private void calculateOverTimeAmount(OverTime overTime) {
        List<AttendanceSummaryDTO> attendanceSummaries = attendanceSummaryService.findAll(overTime.getEmployee().getId(), overTime.getFromDate().atZone(ZoneId.systemDefault()).toLocalDate(), overTime.getToDate().atZone(ZoneId.systemDefault()).toLocalDate());
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

            EmployeeSalary employeeSalaryOfTheDay = employeeSalaryRepository.getOne(summaryDTO.getEmployeeSalaryId());
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
        overTime.setOfficialOverTime(Double.parseDouble((totalOverTimeHour>validOverTimeHour? totalOverTimeHour-validOverTimeHour: totalOverTimeHour)+""));
        overTime.setExtraOverTime(Double.parseDouble(extraOverTimeHour.toString()));
        overTime.setTotalAmount(totalOverTimeSalary);
        overTime.setOfficialAmount(validOverTimeSalary);
        overTime.setExtraAmount(extraOverTimeSalary);
    }
}
