package software.cstl.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Employee;
import software.cstl.domain.EmployeeSalary;
import software.cstl.domain.enumeration.ActiveStatus;
import software.cstl.repository.EmployeeSalaryRepository;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class EmployeeSalaryScheduler {
    private final Logger log = LoggerFactory.getLogger(EmployeeSalaryScheduler.class);

    private final EmployeeSalaryRepository employeeSalaryRepository;

    public EmployeeSalaryScheduler(EmployeeSalaryRepository employeeSalaryRepository) {
        this.employeeSalaryRepository = employeeSalaryRepository;
    }

    @Scheduled(fixedDelay = 90000, initialDelay = 0)
    public void updateNewSalary() throws Exception{
//        log.info("Updating new salary");
//        List<EmployeeSalary> employeeSalaries = employeeSalaryRepository.findAllByStatusAndSalaryStartDateIsBeforeAndSalaryEndDateIsAfter(ActiveStatus.NOT_ACTIVE, Instant.now(), Instant.now());
//        log.info("Total record found: {}", employeeSalaries.size());
//        for(int i=0; i<employeeSalaries.size(); i++){
//            EmployeeSalary currentActiveSalary = employeeSalaryRepository.findByEmployeeAndStatus(employeeSalaries.get(i).getEmployee(), ActiveStatus.ACTIVE);
//            currentActiveSalary.setStatus(ActiveStatus.NOT_ACTIVE);
//            employeeSalaryRepository.save(currentActiveSalary);
//            employeeSalaries.get(i).setStatus(ActiveStatus.ACTIVE);
//        }
//        employeeSalaryRepository.saveAll(employeeSalaries);
    }

}
