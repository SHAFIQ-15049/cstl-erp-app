package software.cstl.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.repository.EmployeeSalaryRepository;

@Service
@Transactional
public class EmployeeSalaryScheduler {
    private final Logger log = LoggerFactory.getLogger(EmployeeSalaryScheduler.class);

    private final EmployeeSalaryRepository employeeSalaryRepository;

    public EmployeeSalaryScheduler(EmployeeSalaryRepository employeeSalaryRepository) {
        this.employeeSalaryRepository = employeeSalaryRepository;
    }
}
