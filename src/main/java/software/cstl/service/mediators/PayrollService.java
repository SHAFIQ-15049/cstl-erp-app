package software.cstl.service.mediators;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.enumeration.MonthType;
import software.cstl.repository.MonthlySalaryDtlRepository;
import software.cstl.repository.MonthlySalaryRepository;
import software.cstl.service.MonthlySalaryService;

@Service
@Transactional
public class PayrollService {
    private final MonthlySalaryRepository monthlySalaryRepository;
    private final MonthlySalaryDtlRepository monthlySalaryDtlRepository;

    public PayrollService(MonthlySalaryRepository monthlySalaryRepository, MonthlySalaryDtlRepository monthlySalaryDtlRepository) {
        this.monthlySalaryRepository = monthlySalaryRepository;
        this.monthlySalaryDtlRepository = monthlySalaryDtlRepository;
    }

    public void createEmptyMonthlySalaries(Integer year, MonthType monthType, Long designationId){

    }

}
