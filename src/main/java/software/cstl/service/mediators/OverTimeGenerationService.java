package software.cstl.service.mediators;

import liquibase.pro.packaged.O;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Employee;
import software.cstl.domain.OverTime;
import software.cstl.domain.enumeration.EmployeeStatus;
import software.cstl.domain.enumeration.MonthType;
import software.cstl.repository.EmployeeRepository;
import software.cstl.repository.extended.EmployeeExtRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class OverTimeGenerationService {
    private final EmployeeExtRepository employeeExtRepository;

    public List<OverTime> generateOverTime(Integer year, MonthType monthType, Long designationId, Instant fromDate, Instant toDate){
        List<OverTime> overTimes = new ArrayList<>();
        List<Employee> employees = employeeExtRepository.findAllByDesignation_IdAndStatus(designationId, EmployeeStatus.ACTIVE);
        for(Employee employee: employees){
            OverTime overTime= new OverTime();
            overTime
                .year(year)
                .month(monthType);
        }
        return overTimes;
    }
}
