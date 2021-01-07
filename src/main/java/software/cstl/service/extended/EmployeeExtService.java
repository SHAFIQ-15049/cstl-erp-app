package software.cstl.service.extended;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.repository.EmployeeRepository;
import software.cstl.service.EmployeeService;

@Service
@Transactional
public class EmployeeExtService extends EmployeeService {
    public EmployeeExtService(EmployeeRepository employeeRepository) {
        super(employeeRepository);
    }
}
